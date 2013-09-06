import org.apache.commons.lang.builder.*;
abstract class GroovyTool  extends Script {
	def tostring(o) {
		return ToStringBuilder.reflectionToString(o, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	def listbeans(){
		def values = this.getBinding().getVariables().values();
		return values;
	}
}