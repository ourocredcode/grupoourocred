<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
$(document).ready(function() { 
	//$("#prazo").mask("99");		
});
</script>

<label for="prazo" class="label_txt">Prazo:</label>
<input class="span10" id="prazo" type="text" name="contrato.prazo" value="${contrato.prazo}" disabled="disabled" required />