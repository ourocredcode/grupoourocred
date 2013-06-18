<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#meiopagamento-li-a').click(function() {
		window.location.href = '<c:url value="/meiopagamento/cadastro" />';
	});

	$('#parceiroinfobanco-li-a').click(function() {
		window.location.href = '<c:url value="/parceiroinfobanco/cadastro" />';
	});

	$('#localidade-li-a').click(function() {
		window.location.href = '<c:url value="/localidade/cadastro" />';
	});

   	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/parceiroinfobanco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$('#parceiroInfoBancoParceiroNegocio').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/parceironegocio/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#parceiroInfoBancoEmpresaId').val() == '' ? '0' :  $('#parceiroInfoBancoEmpresaId').val(), 
	        		  organizacao_id: $('#parceiroInfoBancoOrganizacaoId').val() == '' ? '0' :  $('#parceiroInfoBancoOrganizacaoId').val(),
	        		  nome : $('#agenciaBanco').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#parceiroInfoBancoParceiroNegocio').val('');
         	           $('#parceiroInfoBancoParceiroNegocioId').val('');
         	        }

            	  response($.map(data, function(banco) {  
            		  return {
            			  label: banco.nome,
            			  value: banco.banco_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#agenciaBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#agenciaBanco').val(ui.item.label);
             $('#agenciaBancoId').val(ui.item.value);
             return false;
         }
    });

   $("#parceiroNegocioTipoParceiroId").change(function(evento){

	   var id = $("#parceiroNegocioTipoParceiroId").val();

	   if(id == 1){
		   $("#parceiroNegocioTipoPessoaFisica").css("display", "block");
	   } else {
		   $("#parceiroNegocioTipoPessoaFisica").css("display", "none");
	   }
	   
	   if(id == 2){
		   $("#parceiroNegocioTipoPessoaJuridica").css("display", "block");
	   } else {
		   $("#parceiroNegocioTipoPessoaJuridica").css("display", "none");
	   }

   });
   
   $("#parceiroNegocioIsFuncionario").click(function(evento){

	   if ($('#parceiroNegocioIsFuncionario').is(':checked')){
	         $("#parceiroNegocioFuncionario").css("display", "block");
	      }else{
	         $("#parceiroNegocioFuncionario").css("display", "none");
	      }
	   
   });
   
   $("#parceiroNegocioIsCliente").click(function(evento){

	   if ($('#parceiroNegocioIsCliente').is(':checked')){
	         $("#parceiroNegocioClienteSearch").css("display", "block");
	      }else{
	         $("#parceiroNegocioClienteSearch").css("display", "none");
	      }
	   
   });
   
   $("#parceiroNegocioIsFuncionario").click(function(evento){

	   if ($('#parceiroNegocioIsFuncionario').is(':checked')){
	         $("#parceiroNegocioFuncionarioSearch").css("display", "block");
	      }else{
	         $("#parceiroNegocioFuncionarioSearch").css("display", "none");
	      }
	   
   });
   
   $("#localidadeCep").change(function(){
		var enderecoCEP = $("#localidadeCep").val();
		$.ajax({
	           type: "POST",
	           url: "/sgo/localidade/busca.localidade",
	           data: "enderecoCEP=" + enderecoCEP,
	           beforeSend: function() {
	             $('#alertCEP').html('');
	           },
	           success: function(txt) {
	              if(txt!='ERRO'){
	            	  $('#ajax_endereco').html(txt);
	            	  $('#alertCEP').html('');
	              }else{
	                  $('#alertCEP').html('');
	              }
	           },
	           error: function(txt) {
	             alert('Houve um problema interno. tente novamente mais tarde.');
	           }
	       });
	});

	$("#parceiroInfoBancoIsActive").change(function(e){
		if(document.parceiroInfoBancoForm.parceiroInfoBancoIsActive.checked==true){
			document.parceiroInfoBancoForm.parceiroInfoBancoIsActive.value=true;
		}else{
			document.parceiroInfoBancoForm.parceiroInfoBancoIsActive.value=false;
		}
	});
		
	  
});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.parceiroInfoBancoForm.reset();
	}	
}

function salvaEndereco() {

	var cep = $("#localidadeCep").val();
	var endereco = $("#localidadeEndereco").val();
	var numero = $("#parceirolocalidadeNumero").val();
	var complemento = $("#parceirolocalidadeComplemento").val();
	var pontoreferencia = $("#parceirolocalidadePontoReferencia").val();
	var bairro = $("#localidadeBairro").val();
	var cidadeId = $("#localidadeCidadeId").val();
	var regiaoId = $("#localidadeRegiaoId").val();
	var parceiroId = $("#parceiroNegocioId").val();
	var localidadeId = $("#localidadeId").val();
	var paisId = $("#localidadePaisId").val();

	if (window.confirm("Deseja salvar o endereço?"))
		$.post('<c:url value='/parceiroinfobanco/salvaLocalidade' />',{
				'localidade.cep' : cep ,
				'localidade.endereco' : endereco,
				'localidade.bairro' : bairro,
				'parceiroLocalidade.complemento' : complemento,
				'parceiroLocalidade.pontoReferencia' : pontoreferencia,
				'parceiroLocalidade.numero' : numero,
				'localidade.cidade.cidade_id' : cidadeId,
				'localidade.regiao.regiao_id' : regiaoId,
				'localidade.pais.pais_id' : paisId,
				'localidade.localidade_id' : localidadeId,
				'parceiroLocalidade.parceiroNegocio.parceiroNegocio_id': parceiroId}
		, function(resposta) { 
			if(resposta.indexOf("Erro") != -1){
				alert(resposta);
			} else {
				$('#enderecos').html(resposta);	
			};
		} );

	return false;

}

function salvaContato() {
	
	var parceiroId = $("#parceiroNegocioId").val();
	var parceiroContatoTipoContatoId = $("#parceiroContatoTipoContatoNovo").val();
	var parceiroContatoNome = $("#parceiroContatoNomeNovo").val();

	if (window.confirm("Deseja salvar o endereço?"))
		$.post('<c:url value='/parceiroinfobanco/salvaContato' />',{
				'parceiroContato.tipoContato.tipoContato_id' : parceiroContatoTipoContatoId ,
				'parceiroContato.nome' : parceiroContatoNome,
				'parceiroContato.parceiroNegocio.parceiroNegocio_id ' : parceiroId}
		, function(resposta) { 
			if(resposta.indexOf("Erro") != -1){
				alert(resposta);
			} else {
				$('#parceiroContatosDiv').html(resposta);	
			};
		} );

	return false;

}

function mostraEndereco() {
	
	 $("#ajax_endereco").css("display", "block");
 
	 var mudar = document.getElementById('bttLocalidade');
	 mudar.setAttribute('onclick', 'salvaEndereco()');
	
}

function cancelaEndereco() {
	
	 $("#ajax_endereco").css("display", "none");

	 var mudar = document.getElementById('bttLocalidade');
	 mudar.setAttribute('onclick', 'mostraEndereco()');
	
}

function exclui(linha, id) {

	if (window.confirm("Deseja realmente excluir a Localidade do Parceiro ?"))
		$.post('<c:url value='/parceiroinfobanco/excluiLocalidade' />'
		, {'parceiroLocalidade.parceiroLocalidade_id' : id}
		, function(resposta) { alert(resposta); excluiLinha(linha, resposta); });

	return false;
}

function excluiContato(linha, id) {

	if (window.confirm("Deseja realmente excluir o Contato do Parceiro ?"))
		$.post('<c:url value='/parceiroinfobanco/excluiContato' />'
		, {'parceiroContato.parceiroContato_id' : id}
		, function(resposta) { alert(resposta); excluiLinha(linha, resposta); });

	return false;
}

function excluiLinha(linha, resposta) {

	var objTR = linha.parentNode.parentNode;
	var objTable = objTR.parentNode;

	objTable.deleteRow(objTR.rowIndex - 1);

	return false;
}

function altera(linha, atributo,parceiroLocalidade_id,valor) {

	if(atributo == 'numero'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.numero' : valor };
	}
	
	if(atributo == 'complemento'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.complemento ' : valor };
	}

	if(atributo == 'tipoEndereco'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.tipoEndereco.tipoEndereco_id' : valor} ;
	}

	if (window.confirm("Deseja realmente alterar o atributo do Parceiro Localidade?"))
		$.post('<c:url value='/parceiroinfobanco/alteraParceiroLocalidade' />'
		, attr , function(resposta) { 

				if(resposta.indexOf("Erro") != -1){
					alert(resposta);
					window.location.reload();
				} else {
					alert(resposta);	
				};
				
		});

	return false;
}

function alteraContato(linha, atributo,parceiroContato_id,valor) {

	if(atributo == 'nome'){
		var attr = {'parceiroContato.parceiroContato_id' : parceiroContato_id ,
				 'parceiroContato.nome' : valor };
	}

	if(atributo == 'tipoContato'){
		var attr = {'parceiroContato.parceiroContato_id' : parceiroContato_id ,
				 'parceiroContato.tipoContato.tipoContato_id' : valor} ;
	}

	if (window.confirm("Deseja realmente alterar o atributo do Contato Localidade?"))
		$.post('<c:url value='/parceironegocio/alteraParceiroContato' />'
		, attr , function(resposta) {
				if(resposta.indexOf("Erro") != -1){
					alert(resposta);
					window.location.reload();
				} else {
					alert(resposta);	
				};
				
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Meio Pagamento</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Meio Pagamento</a>
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
				<li class="" id="meiopagamento-li"><a href="#meiopagamento-div" data-toggle="tab" id="meiopagamento-li-a">Cadastro Meio Pagamento</a></li>
				<li class="active" id="parceiroinfobanco-li"><a href="#parceiroinfobanco-div" data-toggle="tab" id="parceiroinfobanco-li-a">Informações Bancárias Parceiros</a></li>
			</ul>
		
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="meiopagamento-div"></div>

				<div class="tab-pane fade active in" id="parceiroinfobanco-div">

					<form id="parceiroInfoBancoForm" name="parceiroInfoBancoForm" action="<c:url value="/parceiroinfobanco/salva"/>" method="POST">												
						<div class="row-fluid">
							<div class="span3">
								<label for="parceiroInfoBancoEmpresa">Empresa</label>
		      					<input class="input-xlarge" id="parceiroInfoBancoEmpresa" name="parceiroInfoBanco.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
		      					<input class="span1" id="parceiroInfoBancoEmpresaId" name="parceiroInfoBanco.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="parceiroInfoBancoOrganizacao">Organização</label>							
	      						<input class="input-xlarge" id="parceiroInfoBancoOrganizacao" name="parceiroInfoBanco.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }">
	      						<input class="span1" id="parceiroInfoBancoOrganizacaoId" name="parceiroInfoBanco.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="parceiroInfoBancoParceiroNegocio">Parceiro</label>							
	      						<input class="input-medium" id="parceiroInfoBancoParceiroNegocio" name="parceiroInfoBanco.parceironegocio.nome" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="parceiroInfoBancoParceiroNegocioId" name="parceiroInfoBanco.parceironegocio.parceironegocio_id" type="hidden">
							</div>
							<div class="span2">
								<label for="parceiroInfoBancoBanco">Banco</label>							
	      						<input class="input-medium" id="parceiroInfoBancoBanco" name="parceiroInfoBanco.banco.nome" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="parceiroInfoBancoBancoId" name="parceiroInfoBanco.banco.banco_id" type="hidden">
							</div>
							<div class="span2">
								<label for="parceiroInfoBancoAgencia">Agência</label>							
	      						<input class="input-medium" id="parceiroInfoBancoAgencia" name="parceiroInfoBanco.agencia.nome" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="parceiroInfoBancoAgenciaId" name="parceiroInfoBanco.agencia.agencia_id" type="hidden">
							</div>
							<div class="span2">
								<label for="parceiroInfoBancoContaBancaria">Conta Bancária</label>							
	      						<input class="input-medium" id="parceiroInfoBancoContaBancaria" name="parceiroInfoBanco.contabancaria.nome" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="parceiroInfoBancoContaBancariaId" name="parceiroInfoBanco.contabancaria.contabancaria_id" type="hidden">
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
					<h5>Banco Produto</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty bancoProdutos}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Banco</th>
									<th>Produto</th>
									<th>Workflow</th>
									<th>IsWflow</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bancoProdutos }" var="bancoProduto">
									<tr>
										<td>${bancoProduto.banco.nome }</td>
										<td>${bancoProduto.produto.nome }</td>
										<td>${bancoProduto.workflow.nome }</td>
										<td>${bancoProduto.isWorkflow}</td>
										<td>${bancoProduto.isActive}</td>
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