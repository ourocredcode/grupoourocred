<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#parceiroInfoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#parceiroInfoBancoEmpresa').val('');
						$('#parceiroInfoBancoEmpresaId').val('');
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
        	 $('#parceiroInfoBancoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#parceiroInfoBancoEmpresa').val(ui.item.label);
             $('#parceiroInfoBancoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#parceiroInfoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#parceiroInfoBancoEmpresaId').val() == '' ? '0' :  $('#parceiroInfoBancoEmpresaId').val(), org_nome : $('#parceiroInfoBancoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#parceiroInfoBancoOrganizacao').val('');
         	            $('#parceiroInfoBancoOrganizacaoId').val('');
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
          	 $('#parceiroInfoBancoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#parceiroInfoBancoOrganizacao').val(ui.item.label);
             $('#parceiroInfoBancoOrganizacaoId').val(ui.item.value);
             return false;
         }
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

		$('#parceiroinfobanco-li-a').click(function() {
			window.location.href = '<c:url value="/parceiroinfobanco/cadastro" />';
		});
		
		$('#localidade-li-a').click(function() {
			window.location.href = '<c:url value="/localidade/cadastro" />';
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
	   
	   $('#bttNovo').click(function() {
			window.location.href = '<c:url value="/parceiroinfobanco/cadastro" />';
		});
});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
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


<div class="container-fluid" id="parceiroinfobanco-div">
	<section id="tabs">
		<ul id="myTab" class="nav nav-tabs">
			<!-- 
			<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
			<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
			<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
			<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
			<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
			<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
			<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
			<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
			<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
			-->
			<li class="active" id="parceiroinfobanco-li"><a href="#parceiroinfobanco-div" data-toggle="tab" id="parceiroinfobanco-li-a">TipoDado BD</a></li>
		</ul>
	
		<div id="myTabContent" class="tab-content">
			<!--
			<div class="tab-pane fade" id="perfil-div"></div>
			<div class="tab-pane fade" id="perfilorgacesso-div"></div>		
			<div class="tab-pane fade" id="janela-div"></div>		
			<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
			<div class="tab-pane fade" id="formulariosjanela-div"></div>			
			<div class="tab-pane fade" id="campoformulario-div"></div>	
			<div class="tab-pane fade " id="tabelabd-div"></div>
			<div class="tab-pane fade " id="colunabd-div"></div>
			<div class="tab-pane fade" id="elementobd-div"></div>
			-->
				<div class="tab-pane fade active in" id="parceiroinfobanco-div">
					<div class="container">
						<form id="parceiroInfoBancoForm" name="parceiroInfoBancoForm" action="<c:url value="/parceiroinfobanco/salva"/>" method="POST">												
							<div class="row-fluid">
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoEmpresa">Empresa</label>
			      					<input id="parceiroInfoBancoEmpresa" name="parceiroInfoBanco.empresa.nome" type="text" required onChange="limpaForm();">
			      					<input id="parceiroInfoBancoEmpresaId" name="parceiroInfoBanco.empresa.empresa_id" type="hidden">
								</div>
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoOrganizacao">Organização</label>							
		      						<input id="parceiroInfoBancoOrganizacao" name="parceiroInfoBanco.organizacao.nome" type="text" required onChange="limpaForm();">
		      						<input id="parceiroInfoBancoOrganizacaoId" name="parceiroInfoBanco.organizacao.organizacao_id" type="hidden">
								</div>
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoParceiroNegocio">Parceiro</label>							
		      						<input id="parceiroInfoBancoParceiroNegocio" name="parceiroInfoBanco.parceironegocio.nome" type="text" required onChange="limpaForm();">
		      						<input id="parceiroInfoBancoParceiroNegocioId" name="parceiroInfoBanco.parceironegocio.parceironegocio_id" type="hidden">
								</div>
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoBanco">Banco</label>							
		      						<input id="parceiroInfoBancoBanco" name="parceiroInfoBanco.banco.nome" type="text" required onChange="limpaForm();">
		      						<input id="parceiroInfoBancoBancoId" name="parceiroInfoBanco.banco.banco_id" type="hidden">
								</div>
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoAgencia">Agência</label>							
		      						<input id="parceiroInfoBancoAgencia" name="parceiroInfoBanco.agencia.nome" type="text" required onChange="limpaForm();">
		      						<input id="parceiroInfoBancoAgenciaId" name="parceiroInfoBanco.agencia.agencia_id" type="hidden">
								</div>
								<div class="span2">
									<label class="control-label" for="parceiroInfoBancoContaBancaria">Conta Bancária</label>							
		      						<input id="parceiroInfoBancoContaBancaria" name="parceiroInfoBanco.contabancaria.nome" type="text" required onChange="limpaForm();">
		      						<input id="parceiroInfoBancoContaBancariaId" name="parceiroInfoBanco.contabancaria.contabancaria_id" type="hidden">
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
		</section>
	</div>

<%@ include file="/footer.jspf"%>
