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

	$('#grupoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#grupoBancoEmpresa').val('');
						$('#grupoBancoEmpresaId').val('');
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
        	 $('#grupoBancoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#grupoBancoEmpresa').val(ui.item.label);
             $('#grupoBancoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#grupoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#grupoBancoEmpresaId').val() == '' ? '0' :  $('#grupoBancoEmpresaId').val(), org_nome : $('#grupoBancoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#grupoBancoOrganizacao').val('');
         	            $('#grupoBancoOrganizacaoId').val('');
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
          	 $('#grupoBancoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#grupoBancoOrganizacao').val(ui.item.label);
             $('#grupoBancoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/grupobanco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#grupoBancoIsActive").change(function(e){
		if(document.grupoBancoForm.grupoBancoIsActive.checked==true){
			document.grupoBancoForm.grupoBancoIsActive.value=true;
		}else{
			document.grupoBancoForm.grupoBancoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.grupoBancoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Grupo Banco</h1>
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
				<li class="" id="banco-li"><a href="#banco-div" data-toggle="tab" id="banco-li-a">Banco</a></li>
				<li class="active" id="grupobanco-li"><a href="#grupobanco-div" data-toggle="tab" id="grupobanco-li-a">Grupo de Bancos</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade " id="banco-div" ></div>

				<div class="tab-pane fade active in" id="grupobanco-div">
					<form id="grupoBancoForm" name="grupoBancoForm" action="<c:url value="/grupobanco/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="grupoBancoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="grupoBancoEmpresa" name="grupoBanco.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="grupoBancoEmpresaId" name="grupoBanco.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
	    					</div>						
							<div class="span3">
								<label for="grupoBancoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="grupoBancoOrganizacao" name="grupoBanco.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="grupoBancoOrganizacaoId" name="grupoBanco.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">	    						
							</div>
							<div class="span3">
								<label for="grupoBancoNome">Nome</label>
								<input class="input-xlarge" id="grupoBancoNome" name="grupoBanco.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span1">
								<label for="grupoBancoIsActive">Ativo</label>
								<input type="checkbox" id="grupoBancoIsActive" name="grupoBanco.isActive" checked="checked" value="1" >							
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
					<c:if test="${not empty gruposBanco}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Grupo Banco</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${gruposBanco }" var="grupoBanco">
									<tr>
										<td>${grupoBanco.nome }</td>
										<td>${grupoBanco.isActive }</td>
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