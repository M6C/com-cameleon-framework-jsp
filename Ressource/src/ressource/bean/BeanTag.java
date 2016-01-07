package ressource.bean;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BeanTag implements Comparable {
	
	private static final String INDENT = "  ";

	private static List listTagNoMandatoryEnd = getListTagNoMandatoryEnd();
	private static Hashtable listTagImpliciteEnd = getHashTagImpliciteEnd();
	private static Random randomId = new Random();
	
	private String id;
	private String name;
	private String tag;
	private String content;
	private Integer count;
	private Integer order;
	private Boolean end;
	private Boolean beginEnd;
	private BeanTag parentTag;
	private String level;
	private Integer row;
	private List listTagChild = new ArrayList();
	private Boolean writed = Boolean.FALSE;
	private Boolean corrected = Boolean.FALSE;
	private Boolean automatic = Boolean.FALSE;
	private List listHistoLevel = new ArrayList();
	private List listAttribut = null;

	public BeanTag(String name, String tag, String content) {
		this(new Long(Math.abs(randomId.nextLong())).toString(), name, tag, content);
	}

	public BeanTag(String id, String name, String tag, String content) {
		super();
		this.id = (id==null) ? id : id.trim();
		this.name = (name==null) ? name : name.trim();
		this.tag = (tag==null) ? tag : tag.trim();
		this.content = (content==null) ? content : content.trim();
		setCount(new Integer(0));
		setOrder(new Integer(0));
		setRow(new Integer(0));
		setEnd(new Boolean(tag.startsWith("</")));
		setBeginEnd(new Boolean(tag.endsWith("/>")));
		if (!isEnd() && !isComment()) {
			this.listAttribut = parseAttribut(tag);
		}
	}

	public void addTagChild(BeanTag beanTag) {
		listTagChild.add(beanTag);
	}

	public boolean equalsLevel(BeanTag obj) {
		return (obj!=null) && (((level==null) && (obj.getLevel()==null)) && ((level!=null) && level.equals(obj.getLevel())));
	}

	/**
	 * Retourne true si ce sont des tag du même nom
	 * et si les attributs de l'objet en paramètre sont au moins tous présent
	 * @param obj
	 * @return
	 */
	public boolean equalsAtLeast(BeanTag obj) {
		return equalsAtLeast(obj, null);
	}

	/**
	 * Retourne true si ce sont des tag du même nom
	 * et si les attributs de l'objet en paramètre sont au moins tous présent
	 * @param obj
	 * @param escapeAttrVal
	 * @return
	 */
	public boolean equalsAtLeast(BeanTag obj, String escapeAttrVal) {
		boolean ret = getName().equalsIgnoreCase(obj.getName());
		if (ret) {
			if (obj.getListAttribut() != null && (obj.getListAttribut().size() > 0)) {
				ret = getListAttribut() != null && getListAttribut().size() >= obj.getListAttribut().size();
				if (ret) {
					int cnt = 0;
					List paramAttribut = obj.getListAttribut();
					for (Iterator iter1 = paramAttribut.iterator(); iter1.hasNext();) {
						BeanTagAttribut elParam = (BeanTagAttribut) iter1.next();
						List attribut = getListAttribut();
						for (Iterator iter2 = attribut.iterator(); iter2.hasNext();) {
							BeanTagAttribut el = (BeanTagAttribut) iter2.next();
							if (escapeAttrVal!=null &&
								(
									escapeAttrVal.equals(elParam.getValue()) ||
									escapeAttrVal.equals(el.getValue())
								)) {
								if (elParam.getName().equals(el.getName())) {
									cnt++;
									break;
								}
							}
							else if (elParam.equals(el)) {
								cnt++;
								break;
							}
						}
					}
					ret = (cnt == paramAttribut.size());
				}
			}
		}
		return ret;
	}

	public BeanTag createEnd() {
		BeanTag tag = new BeanTag(getName(), "</"+getName()+">", "");
		tag.setLevel(getLevel());
		tag.setOrder(getOrder());
		return tag;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(order);
		buf.append("["+name+", "+count+"]");
		buf.append(" (");
		buf.append(isBeginEnd() ? "BE" : (isEnd() ? "E" : "B"));
		buf.append(isCorrected() ? ",Corrected" : "");
		buf.append(isAutomatic() ? ",Automatic" : "");
		buf.append(")");
		return buf.toString();
	}

	public int compareTo(Object o) {
		int ret = 0;
		Object o1 = this;
		Object o2 = o;
		if (o2 instanceof BeanTag)
			ret = (((BeanTag)o1).equalsLevel((BeanTag)o2) ? 0 : 1);
		else
			ret = (o1.equals(o2) ? 0 : 1);
		if (ret!=0) {
			ret = 0;
			String s1 = o1.toString();
			String s2 = o2.toString();
			if (o2 instanceof BeanTag) {
				s1 = ((BeanTag)o1).getLevel();
				s2 = ((BeanTag)o2).getLevel();
			}
			String[] l1 = s1.split("-");
			String[] l2 = s2.split("-");
			int i1 = 0, i2 = 0;
			int len = (l1.length<l2.length) ? l1.length : l2.length;
			for (int i=0 ; i<len && ret==0 ; i++) {
				i1 = Integer.parseInt(l1[i]);
				i2 = Integer.parseInt(l2[i]);
				ret = (i1==i2 ? 0 : i1-i2);
			}
			if (ret==0)
				ret = l1.length-l2.length;
		}
		return ret;
	}

	public void writeHtmlAll(Writer writer) throws IOException {
		writeHtmlAll(writer, false);
	}

	public void writeHtmlAll(Writer writer, boolean indented) throws IOException {
		if (writed.equals(Boolean.FALSE)) {

			if (isComment()) {
				if (indented) writer.write(getIndent());
				writer.write(getTag());//writer.write(getTag()+getContent() + "-->");
				if (indented) writer.write("\r\n");
			}
			else {
				writeBeginTag(writer, indented);	

				if (!getListTagChild().isEmpty()) {
					Iterator it = getListTagChild().iterator();
					while(it.hasNext()) {
						((BeanTag)it.next()).writeHtmlAll(writer);
					}
				}
			}
			setWrited(Boolean.TRUE);
		}
	}
	
	private List parseAttribut(String tag) {
  	List ret = new ArrayList();
  	String[] szaAttr = tag.split(" ");
  	String szAttr = null;
  	for (int i = 0; i < szaAttr.length; i++) {
  		szAttr = szaAttr[i];
  		if (!szAttr.startsWith("<")) {
  			if (szAttr.endsWith(">"))
  				szAttr = szAttr.substring(0, szAttr.length()-1);
  			szAttr = szAttr.trim();
  			if (!szAttr.trim().equals(""))
  				ret.add(new BeanTagAttribut(szAttr));
  		}
		}
  	return ret;
	}

	private void writeBeginTag(Writer writer, boolean indented) throws IOException {
		if (indented) writer.write(getIndent());
  	writer.write(getTag());
  	writeContent(writer, indented);
		if (isCorrected()) writer.write("<!--Corrected-->");
		if (isAutomatic()) writer.write("<!--Automatic-->");
		if (indented) writer.write("\r\n");
	}

	private void writeContent(Writer writer, boolean indented) throws IOException {
  	if (getContent()!=null && !getContent().equals("")) {
  		if (indented) {
	  		writer.write("\r\n");
	  		writer.write(getIndent());
	  		writer.write(INDENT);
  		}
  		writer.write(getContent());
  	}
	}

	public void correctLevel(String level) {
		addListHistoLevet(getLevel());
		setLevel(level);
		setCorrected(Boolean.TRUE);
	}

  // Liste des tags pour les quels une fin de tag n'est pas obligatoire
  public static List getListTagNoMandatoryEnd() {

  	if (listTagNoMandatoryEnd==null) {
  		listTagNoMandatoryEnd = new ArrayList();
	    listTagNoMandatoryEnd.add("BR");
	    listTagNoMandatoryEnd.add("IMG");
	    listTagNoMandatoryEnd.add("INPUT");
	    listTagNoMandatoryEnd.add("META");
	    listTagNoMandatoryEnd.add("LINK");
	    listTagNoMandatoryEnd.add("DOCTYPE");
	    listTagNoMandatoryEnd.add("LI");
	    listTagNoMandatoryEnd.add("AREA");
	    // Commentaire
	    listTagNoMandatoryEnd.add("--");
  	}
    
    return listTagNoMandatoryEnd;
  }

  // Liste des tags avec leur fin de tag implicite
  public static Hashtable getHashTagImpliciteEnd() {

  	if (listTagImpliciteEnd==null) {
  		listTagImpliciteEnd = new Hashtable();
	  	List listEndTag = null;
	  	String tagName = null;
	
	  	/**
	  	 * TABLE
	  	 */
	  	tagName = "TABLE";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("TR");
	  	listTagImpliciteEnd.put(tagName, listEndTag);
	
	  	/**
	  	 * TR
	  	 */
	  	tagName = "TR";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("TD");
	  	listTagImpliciteEnd.put(tagName, listEndTag);
	
	  	/**
	  	 * TD
	  	 */

	  	/**
	  	 * DIV (Identique au tag TD)
	  	 */

	  	/**
	  	 * FORM
	  	 */
  	}

    return listTagImpliciteEnd;
  }

  // Liste des tags avec leur fin de tag implicite
  public static Hashtable getHashTagImpliciteEnd_Complet() {

  	if (listTagImpliciteEnd==null) {
  		listTagImpliciteEnd = new Hashtable();
	  	List listEndTag = null;
	  	String tagName = null;
	
	  	/**
	  	 * TABLE
	  	 */
	  	tagName = "TABLE";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("TR");
	  	listEndTag.add("P");
	  	listTagImpliciteEnd.put(tagName, listEndTag);
	
	  	/**
	  	 * TR
	  	 */
	  	tagName = "TR";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("TD");
	  	listTagImpliciteEnd.put(tagName, listEndTag);
	
	  	/**
	  	 * TD
	  	 */
	  	tagName = "TD";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("SPAN");
	  	listEndTag.add("DIV");
	  	listEndTag.add("FONT");
	  	listEndTag.add("A");
	  	listEndTag.add("B");
	  	listEndTag.add("APPLET");
	  	listEndTag.add("AREA");
	  	listEndTag.add("A");
	  	listEndTag.add("B");
	  	listEndTag.add("CENTER");
	  	listEndTag.add("DIV");
	  	listEndTag.add("EM");
	  	listEndTag.add("FONT");
	  	listEndTag.add("FORM");
	  	listEndTag.add("H1");
	  	listEndTag.add("H2");
	  	listEndTag.add("H3");
	  	listEndTag.add("H4");
	  	listEndTag.add("H5");
	  	listEndTag.add("H6");
	  	listEndTag.add("HR");
	  	listEndTag.add("IMG");
	  	listEndTag.add("INPUT");
	  	listEndTag.add("I");
	  	listEndTag.add("LI");
	  	listEndTag.add("MAP");
	  	listEndTag.add("OL");
	  	listEndTag.add("OPTION");
	  	listEndTag.add("PRE");
	  	listEndTag.add("P");
	  	listEndTag.add("SELECT");
	  	listEndTag.add("SMALL");
	  	listEndTag.add("STRIKE");
	  	listEndTag.add("STRONG");
	  	listEndTag.add("TD");
	  	listEndTag.add("TEXTAREA");
	  	listEndTag.add("TH");
	  	listEndTag.add("TR");
	  	listEndTag.add("UL");
	  	listTagImpliciteEnd.put(tagName, listEndTag);

	  	/**
	  	 * DIV (Identique au tag TD)
	  	 */
	  	tagName = "DIV";
	  	listTagImpliciteEnd.put(tagName, listEndTag);

	  	/**
	  	 * FORM
	  	 */
	  	tagName = "FORM";
	  	listEndTag = new ArrayList();
	  	listEndTag.add("DIV");
	  	listTagImpliciteEnd.put(tagName, listEndTag);
  	}

    return listTagImpliciteEnd;
  }

  // Liste des tags avec leur fin de tag implicite
  public List getListTagImpliciteEnd() {
  	return getListTagImpliciteEnd(getName());
  }

  // Liste des tags avec leur fin de tag implicite
  public static List getListTagImpliciteEnd(String name) {
    return (List)getHashTagImpliciteEnd().get(name);
  }

  public String getIndent() {
		//String[] l = level.split("-");
		String ret = "";
		int len = getRow().intValue();//l.length;
		for(int i=0 ; i<len ; i++) {
			ret+=INDENT;
		}
		return ret;
  }

  public String getEndTag() {
  	String ret = getIndent();
  	ret += ("</" + getName() + ">");
  	ret += ("\r\n");
  	return ret;
  }

  public String getBeginTag() {
  	String indent = getIndent();
  	String ret = indent;
  	ret += getTag();
  	if (getContent()!=null && !getContent().equals("")) {
    	ret += ("\r\n");
	  	ret += indent;
	  	ret += INDENT;
	  	ret += getContent();
  	}
  	ret += ("\r\n");
  	return ret;
  }

  public boolean isMandatoryEnd() {
  	return !listTagNoMandatoryEnd.contains(getName().toUpperCase());
  }

  public boolean isClosingTag(BeanTag tag) {
  	List lTagName = (tag==null) ? null : (List)getListTagImpliciteEnd(getName().toUpperCase());
  	return (lTagName==null) ? false : lTagName.contains(tag.getName().toUpperCase());
  }

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getBeginEnd() {
		return beginEnd;
	}

	public void setBeginEnd(Boolean beginEnd) {
		this.beginEnd = beginEnd;
	}
	
	public boolean isBeginEnd() {
		return getBeginEnd().booleanValue();
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	public boolean isEnd() {
		return getEnd().booleanValue();
	}
	
	public boolean isComment() {
		return "--".equals(getName());
	}

	public boolean isAutomatic() {
		return getAutomatic().booleanValue();
	}

	public List getListTagChild() {
		return listTagChild;
	}

	public void setListTagChild(List listTagChild) {
		this.listTagChild = listTagChild;
	}

	public BeanTag getParentTag() {
		return parentTag;
	}

	public void setParentTag(BeanTag parentTag) {
		this.parentTag = parentTag;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
		// Initialise le rang
		setRow(new Integer(this.level.split("-").length));
	}

	public Boolean getWrited() {
		return writed;
	}

	public void setWrited(Boolean writed) {
		this.writed = writed;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Boolean getCorrected() {
		return corrected;
	}

	public void setCorrected(Boolean corrected) {
		this.corrected = corrected;
	}
	
	public boolean isCorrected() {
		return getCorrected().booleanValue();
	}

	public List getListHistoLevel() {
		return listHistoLevel;
	}

	public void setListHistoLevel(List listHistoLevel) {
		this.listHistoLevel = listHistoLevel;
	}

	public void addListHistoLevet(String level) {
		getListHistoLevel().add(level);
	}

	public Boolean getAutomatic() {
		return automatic;
	}

	public void setAutomatic(Boolean automatic) {
		this.automatic = automatic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getListAttribut() {
		return listAttribut;
	}

	public void setListAttribut(List listAttribut) {
		this.listAttribut = listAttribut;
	}
}
