package com.venky.xml;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLElement extends XMLNode {
    private Element inner = null;
    XMLElement(XMLDocument doc, Element element) {
        super(doc, element);
        this.inner = (Element) getInner();
    }

    public XMLElement cloneElement(final boolean deep) {
        return (XMLElement) cloneNode(deep);
    }

    public String getTagName() {
        return inner.getTagName();
    }

    public boolean hasAttribute(final String name) {
        return inner.hasAttribute(name);
    }

    public String getAttribute(final String name) {
        return inner.getAttribute(name);
    }

    public void setAttribute(String name, Object value) throws DOMException {
        inner.setAttribute(name, String.valueOf(value));
    }

    public void removeAttribute(String name) throws DOMException {
        inner.removeAttribute(name);
    }

    public Iterator<XMLElement> getChildElements() {
        return new ChildElementIterator(inner.getChildNodes());
    }

    public XMLElement getChildElement(String elementName) {
        NodeList list = inner.getElementsByTagName(elementName);
        if (list != null && list.getLength() > 0) {
            Element element = (Element) list.item(0);
            return new XMLElement(getXMLDocument(), element);
        }
        return null;
    }

    public class ChildElementIterator implements Iterator<XMLElement> {
        private final ChildNodeIterator delegate;
        public ChildElementIterator(NodeList children) {
            delegate = new ChildNodeIterator(children, new short[]{Node.ELEMENT_NODE});
        }

        public boolean hasNext() {
            return delegate.hasNext();
        }

        public XMLElement next() {
            return (XMLElement) delegate.next();
        }

        public void remove() {
            delegate.remove();
        }
		
    }

    public XMLElement getParentElement() {
        XMLNode parent = getParentNode();
        if (parent != null && parent.getInner().getNodeType() == Node.ELEMENT_NODE) {
            return (XMLElement) parent;
        }
        return null;
    }
    public XMLElement getNextSiblingElement() {
        XMLNode sibling = this;
        XMLElement ret = null;

        do {
            sibling = sibling.getNextSibling();
            if (sibling.getNodeType() == Node.ELEMENT_NODE) {
                ret = (XMLElement) sibling;
                break;
            }
        } while (sibling != null);

        return ret;
    }

    public XMLElement getPreviousSiblingElement() {
        XMLNode sibling = this;
        XMLElement ret = null;
        do {
            sibling = sibling.getPreviousSibling();
            if (sibling.getNodeType() == Node.ELEMENT_NODE) {
                ret = (XMLElement) sibling;
                break;
            }
        } while (sibling != null);

        return ret;
    }


    public XMLElement insertBefore(XMLElement newChild, XMLElement refChild) throws DOMException {
        return (XMLElement) super.insertBefore(newChild, refChild);
    }

    public XMLElement removeChild(XMLElement oldChild) throws DOMException {
        return (XMLElement) super.removeChild(oldChild);
    }

    public XMLElement getFirstChild() {
        return getChildElements().next();
    }

    @Override
    public String toString() {
        Writer w = new StringWriter();
        XMLSerializationHelper.serialize(inner, w);
        return w.toString();
    }

    public XMLElement createChildElement(String tag) {
        XMLElement elem = getXMLDocument().createElement(tag);
        this.appendChild(elem);
        return elem;
    }
}
