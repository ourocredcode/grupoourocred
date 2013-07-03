<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipotabela-li-a').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});
	
	$('#tabela-li-a').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});

	$('#bancoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/bancoproduto/cadastro" />';
	});

	$('#bancoprodutotabela-li-a').click(function() {
		window.location.href = '<c:url value="/bancoprodutotabela/cadastro" />';
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

	$('#bancoProdutoTabelaTabela').autocomplete({		
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tabela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#bancoProdutoTabelaEmpresaId').val() == '' ? '0' :  $('#bancoProdutoTabelaEmpresaId').val(), 
	        		  organizacao_id : $('#bancoProdutoTabelaOrganizacaoId').val() == '' ? '0' :  $('#bancoProdutoTabelaOrganizacaoId').val(),
	        		  nome : $('#bancoProdutoTabelaTabela').val() },
              success : function(data) {

            	  if (!data || data.length == 0) {
         	            $('#bancoProdutoTabelaTabela').val('');
         	            $('#bancoProdutoTabelaTabelaId').val('');
         	        }

            	  response($.map(data, function(tabela) {  
            		  return {
            			  label: tabela.nome,
            			  value: tabela.tabela_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#bancoProdutoTabelaTabela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#bancoProdutoTabelaTabela').val(ui.item.label);
             $('#bancoProdutoTabelaTabelaId').val(ui.item.value);
             return false;
         }
    });

	$("#bancoProdutoTabelaIsActive").change(function(e){
		if(document.bancoProdutoTabelaForm.bancoProdutoTabelaIsActive.checked==true){
			document.bancoProdutoTabelaForm.bancoProdutoTabelaIsActive.value=true;
		}else{
			document.bancoProdutoTabelaForm.bancoProdutoTabelaIsActive.value=false;
		}
	});

	$('#btnNovo').click(function() {		
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/bancoprodutotabela/cadastro" />';
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.produtoForm.reset();
	}
}

function buscaProdutos(){

	var empresa_id = $('#bancoProdutoTabelaEmpresaId').val();
	var organizacao_id = $('#bancoProdutoTabelaOrganizacaoId').val();
	var banco_id = $('#bancoProdutoTabelaBancoId').val();
	
	$("#bancoProdutoTabelaProdutoId").load('<c:url value="/bancoprodutotabela/produtos" />',			
			{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'banco_id' : banco_id});

}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar o Banco Produto Tabela selecionado?"))
		$.post('<c:url value="/bancoprodutotabela/altera" />', {
			'bancoProdutoTabela.bancoProdutoTabela_id' : id, 'bancoProdutoTabela.isActive' : valor
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Banco Produto Tabela</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Banco Produto Tabela</a>
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
				<li class="" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo Tabela</a></li>
				<li class="" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
				<li class="" id="bancoproduto-li"><a href="#bancoproduto-div" data-toggle="tab" id="bancoproduto-li-a">Banco Produto</a></li>
				<li class="active" id="bancoprodutotabela-li"><a href="#bancoprodutotabela-div" data-toggle="tab" id="bancoprodutotabela-li-a">Banco Produto Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipotabela-div"></div>
				<div class="tab-pane fade" id="tabela-div"></div>
				<div class="tab-pane fade" id="bancoproduto-div"></div>

				<div class="tab-pane fade active in" id="bancoprodutotabela-div">
					<form id="bancoProdutoTabelaForm" name="bancoProdutoTabelaForm" action="<c:url value="/bancoprodutotabela/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">								
								<label for="bancoProdutoTabelaEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="bancoProdutoTabelaEmpresa" name="bancoProdutoTabela.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="bancoProdutoTabelaEmpresaId" name="bancoProdutoTabela.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span3">
								<label for="bancoProdutoTabelaOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="bancoProdutoTabelaOrganizacao" name="bancoProdutoTabela.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="bancoProdutoTabelaOrganizacaoId" name="bancoProdutoTabela.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="bancoProdutoTabelaBanco">Banco</label>
								<select id="bancoProdutoTabelaBancoId" name="bancoProdutoTabela.banco.banco_id" class="input-medium" onchange="buscaProdutos();">
									<c:if test="${bancos != null}">
										<option value="">Selecione o banco</option>
										<c:forEach var="banco" items="${bancos }">
										 	<option value="${banco.banco_id }"> ${banco.nome }</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="span2">
	      						<label for="bancoProdutoTabelaProdutoId">Produto</label>
	      						<select id="bancoProdutoTabelaProdutoId" name="bancoProdutoTabela.produto.produto_id" class="input-medium">
	      							<option value="">Selecion um Banco...</option>
	      						</select>
							</div>
							<div class="span2">
								<label for="bancoProdutoTabelaTabela">Tabela</label>
								<input class="input-medium" id="bancoProdutoTabelaTabela" name="bancoProdutoTabela.tabela.nome" type="text" value="${bancoProdutoTabela.tabela.nome }" required >
	      						<input class="span1" id="bancoProdutoTabelaTabelaId" name="bancoProdutoTabela.tabela.tabela_id" type="hidden">			    					
							</div>
							<div class="span2">
								<label for="bancoProdutoTabelaPrazo">Prazo</label>
								<input class="input-mini" type="text" id="bancoProdutoTabelaNome" name="bancoProdutoTabela.prazo" placeholder="Nome" value="${bancoProdutoTabela.prazo }" required>
							</div>
							<div class="span1">
								<label for="bancoProdutoTabelaIsActive">Ativo</label>									
								<input type="checkbox" id="bancoProdutoTabelaIsActive" name="bancoProdutoTabela.isActive" checked="checked" value="1" >
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
					<h5>Banco Produto Tabela</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty bancoProdutoTabelas}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Banco</th>
									<th>Produto</th>
									<th>Tabela</th>
									<th>Prazo</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bancoProdutoTabelas }" var="bancoProdutoTabela">
									<tr>
										<td>${bancoProdutoTabela.banco.nome }</td>
										<td>${bancoProdutoTabela.produto.nome }</td>
										<td>${bancoProdutoTabela.tabela.nome }</td>
										<td>${bancoProdutoTabela.prazo }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="bancoProdutoTabelaIsActiveLine" name="bancoProdutoTabela.isActive"
												<c:if test="${bancoProdutoTabela.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${bancoProdutoTabela.bancoProdutoTabela_id }');">
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