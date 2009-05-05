package org.mozilla.javascript.xmlimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XHTMLProcessor extends XmlProcessor {

	public XHTMLProcessor() {
		// TODO Auto-generated constructor stub
		this.setIgnoreWhitespace(false);
	}
	
	protected String elementToXmlString(Element element){
		StringBuffer buf = new StringBuffer();
		String tagName = element.getTagName();

		buf.append("<");
		buf.append(tagName);
		if(element.hasAttributes()){
			NamedNodeMap attrs = element.getAttributes();
			int len = attrs.getLength();
			for(int i=0; i < len; i++){
				Node attr = attrs.item(i);
				String name = attr.getNodeName();

				// empty namespaces seem to still be attached to Nodes after calling removeNamespace
				if(name.startsWith("xmlns") && ((Attr) attr).getValue().equals("")){
					continue;
				}
				
				buf.append(" ");
				buf.append(name);
				buf.append("=\"");
				buf.append(ecmaToXmlString(attrs.item(i)));
				buf.append("\"");
				
			}
		}
		if(!element.hasChildNodes()){
		    // these tags should be self-closing singletons
			if(tagName.equals("input") ||
			   tagName.equals("img") ||
			   tagName.equals("br") ||
			   tagName.equals("hr") ||
			   tagName.equals("frame") ||
			   tagName.equals("link") ||
			   tagName.equals("meta") ||
			   tagName.equals("param")
			){
				buf.append("/>");
 			} else {
				buf.append("></");
				buf.append(tagName);
				buf.append(">");
			}
		} else {
			buf.append(">");

			NodeList children = element.getChildNodes();
			int len = children.getLength();
			for(int i=0; i < len; i++){
				buf.append(ecmaToXmlString(children.item(i)));
			}
			
			buf.append("</");
			buf.append(tagName);
			buf.append(">");
		}
		return buf.toString();
	}

}
