<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfilorgacesso/cadastro" />';
	});

	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
	});
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#campoformulario-li-a').click(function() {
		window.location.href = '<c:url value="/campoformulario/cadastro" />';
	});

	$('#tabelabd-li-a').click(function() {
		window.location.href = '<c:url value="/tabelabd/cadastro" />';
	});

	$('#colunabd-li-a').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});

	$('#elementobd-li-a').click(function() {
		window.location.href = '<c:url value="/elementobd/cadastro" />';
	});

	$('#tipodadobd-li-a').click(function() {
		window.location.href = '<c:url value="/tipodadobd/cadastro" />';
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

	$('#perfilOrgAcessoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#perfilOrgAcessoEmpresa').val('');
						$('#perfilOrgAcessoEmpresaId').val('');
           	        }

            	  response($.map(data, function(empresa) {  
            		  return {
                          label: empresa.nome,
                          value: empresa.empresa_id
                      };
                  }));  
               }
	        });
         } ,
         focus: function( event, ui ) {
        	 $('#perfilOrgAcessoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#perfilOrgAcessoEmpresa').val(ui.item.label);
             $('#perfilOrgAcessoEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#perfilOrgAcessoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilOrgAcessoEmpresaId').val() == '' ? '0' :  $('#perfilOrgAcessoEmpresaId').val(), org_nome : $('#perfilOrgAcessoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#perfilOrgAcessoOrganizacao').val('');
         	           $('#perfilOrgAcessoOrganizacaoId').val('');
         	        }

            	  response($.map(data, function(organizacao) {  
            		  return {
            			  label: organizacao.nome,
            			  value: organizacao.organizacao_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#perfilOrgAcessoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#perfilOrgAcessoOrganizacao').val(ui.item.label);
             $('#perfilOrgAcessoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#perfilOrgAcessoPerfil').autocomplete({		
		source: function( request, response ) {

	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilOrgAcessoEmpresaId').val() == '' ? '0' :  $('#perfilOrgAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#perfilOrgAcessoOrganizacaoId').val() == '' ? '0' :  $('#perfilOrgAcessoOrganizacaoId').val(),
	        		  nome : $('#perfilOrgAcessoPerfil').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#perfilOrgAcessoPerfil').val('');
         	           $('#perfilOrgAcessoPerfilId').val('');
         	        }

            	  response($.map(data, function(perfil) {  
            		  return {
            			  label: perfil.nome,
            			  value: perfil.perfil_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#perfilOrgAcessoPerfil').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#perfilOrgAcessoPerfil').val(ui.item.label);
             $('#perfilOrgAcessoPerfilId').val(ui.item.value);
             return false;
         }
    });
	
	$("#perfilOrgAcessoIsActive").change(function(e){
		if(document.perfilOrgAcessoForm.perfilOrgAcessoIsActive.checked==true){
			document.perfilOrgAcessoForm.perfilOrgAcessoIsActive.value=true;
		}else{
			document.perfilOrgAcessoForm.perfilOrgAcessoIsActive.value=false;
		}
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/perfilorgacesso/cadastro" />';
	});

});

function altera(linha, empresa, organizacao, perfil) {
	var emp = empresa;
	var org = organizacao;
	var perf = perfil;

	var valor = linha.checked == true ? true : false;

	if (window.confirm("Deseja alterar o dado selecionado?"))
		$.post('<c:url value='/perfiloorgacesso/altera' />'
			,{'empresa_id': emp, 'organizacao_id' : org, 'perfil_id' : perf, 'isActive' : valor}
		);

	return false;
}

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.perfilOrgAcessoForm.reset();
	}
}


</script>

<div id="content-header">
	<h1>Cadastro Perfil</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">Perfil</a>
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
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="active" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="perfil-div"></div>
				
				<div class="tab-pane fade active in" id="perfilorgacesso-div">				
						
					<form id="perfilOrgAcessoForm" name="perfilOrgAcessoForm" action="<c:url value="/perfilorgacesso/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">								
								<label for="perfilOrgAcessoEmpresa">Empresa</label>							
	      						<input class="input-xlarge" id="perfilOrgAcessoEmpresa" name="perfilOrgAcesso.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="perfilOrgAcessoEmpresaId" name="perfilOrgAcesso.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    					
							</div>
							<div class="span3">
								<label for="perfilOrgAcessoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="perfilOrgAcessoOrganizacao" name="perfilOrgAcesso.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required >
	      						<input class="span1" id="perfilOrgAcessoOrganizacaoId" name="perfilOrgAcesso.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="perfilOrgAcessoPerfil">Perfil</label>								
	      						<input class="input-medium" id="perfilOrgAcessoPerfil" name="perfilOrgAcesso.perfil.nome" type="text" required value="${perfilOrgAcesso.perfil.nome }" >
	      						<input class="span1" id="perfilOrgAcessoPerfilId" name="perfilOrgAcesso.perfil.perfil_id" value="${perfilOrgAcesso.perfil.perfil_id }" type="hidden">
	    					</div>
							<div class="span1">
								<label  for="perfilOrgAcessoIsActive">Ativo</label>
								<input type="checkbox" id="perfilOrgAcessoIsActive" name="perfilOrgAcesso.isActive" checked="checked" value="1">
							</div>
						</div>
						<div class="btn-toolbar">
							<div class="btn-group">
								<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
							</div>	
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
							</div>
						</div>
					</form>
				</div>

				<div class="tab-pane fade" id="janela-div"></div>				
				<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
				<div class="tab-pane fade" id="formulariosjanela-div"></div>
				<div class="tab-pane fade" id="campoformulario-div"></div>
				<div class="tab-pane fade" id="tabelabd-div"></div>
				<div class="tab-pane fade" id="colunabd-div"></div>
				<div class="tab-pane fade" id="elementobd-div"></div>
				<div class="tab-pane fade" id="tipodadobd-div"></div>

			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i>
					</span>
					<h5>Perfil Organização</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty perfisOrgAcesso}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Perfil</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${perfisOrgAcesso }" var="perfilOrgAcesso">
									<tr>
										<td>${perfilOrgAcesso.empresa.nome }</td>
										<td>${perfilOrgAcesso.organizacao.nome }</td>
										<td>${perfilOrgAcesso.perfil.nome }</td>
										<td>
										<label class="checkbox inline">
											<input type="checkbox" id="isActiveLine" name="perfilOrgAcesso.isActive"
											<c:if test="${perfilOrgAcesso.isActive == true }"> checked="checked"</c:if> onchange="altera(this, '${perfilOrgAcesso.empresa.empresa_id }', '${perfilOrgAcesso.organizacao.organizacao_id }', '${perfilOrgAcesso.perfil.perfil_id }');">
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