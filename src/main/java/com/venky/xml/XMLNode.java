package com.venky.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode {
    private final Node inner;
    private final XMLDocument doc;
    public XMLNode(final XMLDocument doc, final Node node) {
        this.doc = doc;
        this.inner = node;
    }

    public XMLDocument getXMLDocument() {
        return doc;
    }

    public Map<String, String> getAttributes() {
        Map<String, String> map = new HashMap<String, String>();

        NamedNodeMap attrs = inner.getAttributes();
        if (attrs == null) {
            return map;
        }
        for (int i = 0; i < attrs.getLength(); i++) {
            Node e = attrs.item(i);
            map.put(e.getNodeName(), e.getNodeValue());
        }
        return map;
    }

	
    public Node getInner() {
        return inner;
    }

    public String getNodeName() {
        return inner.getNodeName();
    }

    public void setNodeValue(String nodeValue) throws DOMException {
        if (inner.getNodeType() != Node.ELEMENT_NODE){
            inner.setNodeValue(nodeValue);
        }else {
            Node node = doc.getDocument().createTextNode(nodeValue);
            inner.appendChild(node);
        }
    }

    public String getNodeValue() throws DOMException {
        if (inner.getNodeType() != Node.ELEMENT_NODE){
            return inner.getNodeValue();
        }else {
            
            NodeList nl = inner.getChildNodes()    ;
            for (int i = 0; i < nl.getLength() ; i++){
                if (nl.item(i).getNodeType() == Node.TEXT_NODE){
                    return nl.item(i).getNodeValue();
                }
            }
            return inner.getNodeValue();
        }
    }

    public int getNodeType() {
        return inner.getNodeType();
    }

    public boolean hasAttributes() {
        return inner.hasAttributes();
    }

    public boolean hasChildNodes() {
        return inner.hasChildNodes();
    }

    public Document getOwnerDocument() {
        return inner.getOwnerDocument();
    }

    public void appendChild(XMLNode child) {
        if (getOwnerDocument() != child.getOwnerDocument()) {
            getOwnerDocument().importNode(child.inner, true);
        }
        inner.appendChild(child.inner);
    }
    public XMLNode insertBefore(XMLNode newChild, XMLNode refChild) throws DOMException {
        return getXMLNode(inner.insertBefore(newChild.inner, refChild.inner));
    }
    public XMLNode getParentNode() {
        Node parent = inner.getParentNode();
        return getXMLNode(parent);
    }

    public XMLNode cloneNode(boolean deep) {
        Node clone = inner.cloneNode(deep);
        return getXMLNode(clone);
    }

    public XMLNode getNextSibling() {
        Node sibling = inner.getNextSibling();
        return getXMLNode(sibling);
    }

    public XMLNode getPreviousSibling() {
        Node sibling = inner.getPreviousSibling();
        return getXMLNode(sibling);
    }

    public XMLNode removeChild(XMLNode node) {
        return getXMLNode(inner.removeChild(node.inner));
    }

    public Iterator<XMLNode> getChildren() {
        NodeList nodeList = inner.getChildNodes();
        return new ChildNodeIterator(nodeList);
    }

    public class ChildNodeIterator implements Iterator<XMLNode> {
        private Iterator<XMLNode> delegate = null;
        public ChildNodeIterator(NodeList children) {
            this(children, new short[]{});
        }
        public ChildNodeIterator(NodeList children, short[] nodeType) {
        	this(children,nodeType, null);
        }
        public ChildNodeIterator(NodeList children, short[] nodeType,String nodeName) {
            List<XMLNode> colChildren = new ArrayList<XMLNode>();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node node = children.item(i);
                    if (isNodeOfType(node, nodeType)) {
                    	if (nodeName == null || nodeName.equals(node.getNodeName())) {
                            colChildren.add(getXMLNode(node));
                    	}
                    }
                }
            }
            delegate = colChildren.iterator();
        }

        private boolean isNodeOfType(Node node, short[] nodeType) {
            if (nodeType == null || nodeType.length == 0) {
                return true;
            }
            for (int i = 0; i < nodeType.length; i++) {
                if (node.getNodeType() == nodeType[i]) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasNext() {
            return delegate.hasNext();
        }

        public XMLNode next() {
            return delegate.next();
        }

        public void remove() {
            delegate.remove();
        }
    }

    private XMLNode getXMLNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return new XMLElement(doc, (Element) node);
        } else {
            return new XMLNode(doc, node);
        }

    }
	

}
