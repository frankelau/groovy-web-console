import org.apache.commons.lang.builder.*;
abstract class GroovyTool  extends Script {
	def tostring(o) {
		return ToStringBuilder.reflectionToString(o, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	def ls(){
		def re ="";
		def values = this.getBinding().getVariables().keySet();
		for(value in  values){
			re=re+value;
		}
		return value;
	}
}