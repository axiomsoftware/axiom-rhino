package org.mozilla.javascript.xmlimpl;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.xml.XMLObject;
import org.mozilla.javascript.xmlimpl.XMLLibImpl;
import org.mozilla.javascript.xmlimpl.XMLList;

public class XHTML extends XMLList implements Function{
	private static final long serialVersionUID = 1L;

	private XMLLibImpl lib;
	public XHTML(XMLLibImpl lib, Scriptable scope, XMLObject prototype) {
		super(lib, scope, prototype);
		this.lib = lib;
	}

    public String getClassName() {
        return "XHTML";
    }
    
    protected Object jsConstructor(Context cx, boolean inNewExpr, Object[] args) {
            if(args.length > 0){
            	Object arg0 = args[0];
            	if(arg0 instanceof String || arg0 instanceof Scriptable && ((Scriptable) arg0).getClassName().equals("String")){
            		// escape entities and strip leading declarations and doctypes
            		arg0 = arg0.toString().replaceAll("&", "&amp;").replaceFirst("^\\s*<[\\!\\?][^>]*>", "");
            	}
            	return lib.newXHTMLFrom(arg0);
            }
            return lib.newXHTML();
    }
    
    public String toXMLString(){
    	return super.toXMLString().replaceAll("&amp;", "&");
    }
    
    XMLList newXMLList(){
    	return lib.newXHTML();
    }
}
