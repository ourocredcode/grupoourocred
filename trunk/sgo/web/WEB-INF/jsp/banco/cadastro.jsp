<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupobanco-li-a').click(function() {
		window.location.href = '<c:url value="/grupobanco/cadastro" />';
	});

	$('#banco-li-a').click(function() {
		window.location.href = '<c:url value="/banco/cadastro" />';
	});

	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();

	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});
	
	$('#bancoGrupoBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/grupobanco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#bancoEmpresaId').val() == '' ? '0' :  $('#bancoEmpresaId').val(), 
	        		  organizacao_id : $('#bancoOrganizacaoId').val() == '' ? '0' :  $('#bancoOrganizacaoId').val(),
	        		  nome : $('#bancoGrupoBanco').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#bancoGrupoBanco').val('');
         	            $('#bancoGrupoBancoId').val('');
         	        }

            	  response($.map(data, function(grupoBanco) {  
            		  return {
            			  label: grupoBanco.nome,
            			  value: grupoBanco.grupoBanco_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#bancoGrupoBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#bancoGrupoBanco').val(ui.item.label);
             $('#bancoGrupoBancoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/banco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.bancoForm.reset();
	});

	$("#bancoIsRecompra").change(function(e){
		if(document.bancoForm.bancoIsRecompra.checked==true){
			document.bancoForm.bancoIsRecompra.value=true;
		}else{
			document.bancoForm.bancoIsRecompra.value=false;
		}
	});

	$("#bancoIsActive").change(function(e){
		if(document.bancoForm.bancoIsActive.checked==true){
			document.bancoForm.bancoIsActive.value=true;
		}else{
			document.bancoForm.bancoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.bancoForm.reset();
	}
}

function altera(linha, atributo, id) {
	if(atributo == 'isCompradoLine'){
		var isComprado = linha.checked == true ? true : false;
		if (window.confirm("Deseja alterar o Banco selecionado?"))
			$.post('<c:url value="/banco/altera" />', {
				'banco.banco_id' : id, 'banco.isComprado' : isComprado
			});
	}	
	if(atributo=='isActiveLine'){
		var isActive = linha.checked == true ? true : false;
		if (window.confirm("Deseja alterar o Banco selecionado?"))
			$.post('<c:url value="/banco/altera" />', {'banco.banco_id' : id, 'banco.isActive' : isActive});
	}
	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Banco</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Banco</a>
</div>

<c:if test="${not empty notice}">
	<c:choose>
		<c:when test="${fn:contains(notice,'Erro:')}">
				<div class="alert alert-error">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:when>
		<c:otherwise>
				<div class="alert alert-success">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:otherwise>
	</c:choose>
</c:if>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<ul id="myTab" class="nav nav-tabs">								
				<li class="active" id="banco-li"><a href="#banco-div" data-toggle="tab" id="banco-li-a">Banco</a></li>
				<li class="" id="grupobanco-li"><a href="#grupobanco-div" data-toggle="tab" id="grupobanco-li-a">Grupo de Bancos</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="banco-div" >
					<form id="bancoForm" name="bancoForm" action="<c:url value="/banco/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span4">
								<label for="bancoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="bancoEmpresa" name="banco.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="bancoEmpresaId" name="banco.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span4">
								<label for="bancoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="bancoOrganizacao" name="banco.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="bancoOrganizacaoId" name="banco.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
	    					</div>
    					</div>
    					<div class="row-fluid">
	    					<div class="span2">
								<label for="bancoGrupoBanco">Grupo Banco</label>
								<select id="bancoGrupoBancoId" name="banco.grupoBanco.grupoBanco_id" class="input-medium" >
									<c:forEach var="grupoBanco" items="${gruposBanco }">
									 	<option value="${grupoBanco.grupoBanco_id }" selected="selected"> ${grupoBanco.nome } </option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="bancoClassificacaoBanco">Classificação Banco</label>
								<select id="bancoClassificacaoBancoId" name="banco.classificacaoBanco.classificacaoBanco_id" class="input-medium" >
									<c:forEach var="classificacaoBanco" items="${classificacaoBancos }">
									 	<option value="${classificacaoBanco.classificacaoBanco_id }" selected="selected"> ${classificacaoBanco.nome } </option>
									</c:forEach>
								</select>
							</div>	    					
							<div class="span3">
								<label for="bancoNome">Nome</label>
								<input class="input-xlarge" type="text" id="bancoNome" name="banco.nome" placeholder="Nome" value="${banco.nome }" required>
							</div>
							<div class="span1">
								<label for="bancoIsComprado">Comprado</label>
								<input type="checkbox" id="bancoIsComprado" name="banco.isComprado" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="bancoIsActive">Ativo</label>
								<input type="checkbox" id="bancoIsActive" name="banco.isActive" checked="checked" value="1" >
							</div>
						</div>
					 	<div class="btn-group">
							<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
						</div>
					</form>
				</div>

				<div class="tab-pane fade" id="grupobanco-div"></div>

			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i> </span>
					<h5>Bancos</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty bancos}">
						<table class="table table-bordered table-striped table-hover data-table">
							<thead>
								<tr>
									<th>Banco</th>
									<th>Grupo Banco</th>
									<th>Classificação Banco</th>
									<th>Comprado</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bancos }" var="banco">
									<tr>
										<td>${banco.nome }</td>
										<td>${banco.grupoBanco.nome }</td>
										<td>${banco.classificacaoBanco.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isCompradoLine" name="banco.isComprado"
												<c:if test="${banco.isComprado == true }"> checked="checked"</c:if> onchange="altera(this,'isCompradoLine','${banco.banco_id}');">
											</label>
										</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="banco.isActive"
												<c:if test="${banco.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${banco.banco_id}');">
											</label>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>