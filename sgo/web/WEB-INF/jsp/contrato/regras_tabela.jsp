<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<fmt:setLocale value="pt-BR" /> 

<script type="text/javascript">
	
$(document).ready(function() {

	$("#valorMeta").maskMoney({symbol:"",decimal:".",thousands:"",precision:2});
	$("#valorComissao").maskMoney({symbol:"",decimal:".",thousands:"",precision:2});

});

function zeravalorparcela(){

	var contratoId = document.getElementById("contratoId");
	var valorParcela = document.getElementById("valorParcela");

	if(contratoId.value != ''){
		valorMeta.value = '0.0';
	} else {
		valorParcela.value = '';
		valorParcela.focus();
	}
}
	
</script>

<div class="span3">
	<label for="prazo">Prazo:</label>
	<input id="prazo" class="span10"  type="text" value="${contrato.prazo }" name="contrato.prazo"  disabled="disabled" required />
</div>
<div class="span3">
	<label for="valorMeta">Valor Meta</label>
	<input id="valorMeta" type="text" class="span10" value="${contrato.valorMeta }"  name="contrato.valorMeta" <c:if test="${usuarioInfo.perfil.chave != 'Gestor' }">disabled="disabled"</c:if> />	
</div>
<div class="span3">
	<label for="valorComissao">Valor Comissao</label>
	<input id="valorComissao" type="text" class="span10"  value="${contrato.valorComissao }" name="contrato.valorComissao" <c:if test="${usuarioInfo.perfil.chave != 'Gestor' }">disabled="disabled"</c:if> />	
</div>

<c:if test="${not empty notice}">

	<script>
		zeravalorparcela();
	</script>
	
	<div class="row-fluid">
		<div class="span9">
			<c:choose>
			<c:when test="${fn:contains(notice,'Erro:')}">
					<div class="alert alert-error">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:when>
			</c:choose>
		</div>
	</div>
</c:if>


