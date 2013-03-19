<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
		$("#localidadeCep").change(function(){
			var enderecoCEP = $("#localidadeCep").val();
			$.ajax({
		           type: "POST",
		           url: "/sgo/localidade/busca.localidade",
		           data: "enderecoCEP=" + enderecoCEP,
		           beforeSend: function() {
		             $('#alertCEP').html('Processando...');
		           },
		           success: function(txt) {
		              if(txt!='ERRO'){
		            	  $('#ajax_endereco').html(txt);
		            	  $('#alertCEP').html('CEP Encontrado');
		              }else{
		                  $('#alertCEP').html('CEP Inexistente');
		              }
		           },
		           error: function(txt) {
		             alert('Houve um problema interno. tente novamente mais tarde.');
		           }
		       });
		});

	   $("#parceiroNegocioTipo").change(function(evento){

		   var parceiroNegocioTipo = $("#parceiroNegocioTipo").val();
		   
		   if(parceiroNegocioTipo == 'PessoaFisica'){
			   $("#parceiroNegocioTipoPessoaFisica").css("display", "block");
		   } else {
			   $("#parceiroNegocioTipoPessoaFisica").css("display", "none");
		   }
		   
		   if(parceiroNegocioTipo == 'PessoaJuridica'){
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

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
	}	
}
</script>

	<div class="container-fluid" id="parceiroNegocio-div">	

		<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="/parceironegocio/salva"/>" method="POST">

			<div class="row-fluid">

				<div class="span2">
					<label class="control-label" for="parceiroNegocioEmpresa">Empresa</label>
					<input id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" value="${usuarioInfo.usuario.empresa.nome }" type="text" required>
					<input id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden" value="${usuarioInfo.usuario.empresa.empresa_id }">
				</div>
				<div class="span2">
					<label class="control-label" for="parceiroNegocioOrganizacao">Organização</label>
					<input id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" value="${usuarioInfo.usuario.organizacao.nome }" type="text" required>
					<input id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id"  value="${usuarioInfo.usuario.organizacao.organizacao_id }" type="hidden">
				</div>
				<div class="span4">
					<label class="control-label">Tipo de Cadastro</label>	
					<label class="checkbox inline">
						<input type="checkbox" id="parceiroNegocioIsFuncionario" name="parceiroNegocio.isFuncionario" value="1"> Funcionário
					</label>
					<label class="checkbox inline">
						<input type="checkbox" id="parceiroNegocioIsCliente" name="parceiroNegocio.isCliente" value="1"> Cliente
					</label>
					<label class="checkbox inline">
						<input type="checkbox" id="parceiroNegocioIsFornecedor" name="parceiroNegocio.isFornecedor" value="1"> Fornecedor
					</label>
				</div>	
	
			</div>

			<div class="row-fluid">
				<div class="span2"> 
					<label class="control-label">Tipo</label>	
					<select id="parceiroNegocioTipo">
						<option value=""> Escolha o Tipo </option>
						<option value="PessoaFisica"> Pessoa Fisica </option>
						<option value="PessoaJuridica"> Pessoa Juridica </option>
					</select>
				</div>
				<div class="span2">
					<label class="control-label" for="parceiroNegocioCategoriaParceiro">Categoria</label>
					<input  class="input-large" id="parceiroNegocioCategoriaParceiro" name="parceiroNegocio.categoriaParceiro.nome" type="text" value="Serviço">
					<input  class="input-large" id="parceiroNegocioCategoriaParceiroId" name="parceiroNegocio.categoriaParceiro.categoriaParceiro_id" type="hidden" value="1">
				</div>
				<div class="span2">
					<label class="control-label" for="parceiroNegocioClassificacaoParceiro">Classificacao</label>
					<input  class="input-large" id="parceiroNegocioClassificacaoParceiro" name="parceiroNegocio.classificacaoParceiro.nome" type="text" value="Normal">
					<input  class="input-large" id="parceiroNegocioClassificacaoParceiroId" name="parceiroNegocio.classificacaoParceiro.classificacaoParceiro_id" type="hidden" value="1">
				</div>
				<div class="span2">
					<label class="control-label" for="parceiroNegocioGrupoParceiro">Grupo</label>
					<input  class="input-large" id="parceiroNegocioGrupoParceiro" name="parceiroNegocio.grupoParceiro.nome" type="text" value="Teste">
					<input  class="input-large" id="parceiroNegocioGrupoParceiroId" name="parceiroNegocio.grupoParceiro.grupoParceiro_id" type="hidden" value="1">
				</div>
			</div>

			<div class="row-fluid">

				<div class="span3">
					<label class="control-label" for="parceiroNegocioNome">Nome</label>
					<input  class="input-xlarge" id="parceiroNegocioNome" name="parceiroNegocio.nome" type="text">
				</div>

				<div id="parceiroNegocioTipoPessoaFisica" class="row-fluid" style="display: none;">
					<div class="span2" >
						<label class="control-label" for="parceiroNegocioCpf">CPF</label>
						<input  class="input-large" id="parceiroNegocioCpf" name="parceiroNegocio.cpf" type="text">
					</div>
					<div class="span2">
						<label class="control-label" for="parceiroNegocioRg">RG</label>
						<input  class="input-large" id="parceiroNegocioRg" name="parceiroNegocio.rg" type="text">
					</div>
				</div>

				<div id="parceiroNegocioTipoPessoaJuridica" class="row-fluid" style="display: none;">
					<div class="span2" >
						<label class="control-label" for="parceiroNegocioIe">CNPJ</label>
						<input  class="input-large" id="parceiroNegocioIe" name="parceiroNegocio.ie" type="text">
					</div>
					<div class="span2" >
						<label class="control-label" for="parceiroNegocioIe">Inscrição Estadual</label>
						<input  class="input-large" id="parceiroNegocioIe" name="parceiroNegocio.ie" type="text">
					</div>
				</div>
			</div>

			<div id="ajax_endereco">

				<div class="controls controls-row">
					<input class="span2" id="localidadeCep" name="localidadeCep" type="text" placeholder="Busca Cep" />
				</div>
				<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 180px;margin-top: -27px;"><i>Digite para buscar</i></div>

				<div class="controls controls-row">
					<input class="span7" id="localidadeEndereco" name="localidadeEndereco" type="text" placeholder="Endereço"/>
					<input class="span1" id="localidadeNumero" name="localidadeNumero" type="text" placeholder="Número"/>
				</div>

				<div class="controls controls-row">
					<input class="span3" id="localidadeComplemento" name="localidadeComplemento" type="text" placeholder="Complemento" />
					<input class="span2" id="localidadeBairro" name="localidadeBairro" type="text" placeholder="Bairro" />
					<input class="span2" id="localidadeCidade" name="localidadeCidade" type="text" placeholder="Cidade" />
					<input class="span1" id="localidadeRegiao" name="localidadeRegiao" type="text" placeholder="UF"  />
				</div>

			</div>

			<div class="row-fluid">
				<label class="checkbox">
						<input type="checkbox" id="parceiroNegocioIsActive" name="parceiroNegocio.isActive" checked="checked" value="1"> Ativo
				</label>							
			</div>

			<div id="parceiroNegocioFuncionario" class="row-fluid" style="display: none;">
				<div class="row-fluid">
					<div class="span2" >
						<label class="control-label" for="funcionarioDepartamento">Departamento</label>
						<select  id="funcionarioDepartamento" name="funcionario.departamento" >
							<option value="">Selecione</option>
							<c:forEach var="departamento" items="${departamentos }">
								<option value="${departamento.departamento_id }"> ${departamento.nome }</option>
							</c:forEach>
						</select>
					</div>
					<div class="span2">
						<label class="control-label" for="funcionarioFuncao">Função</label>
						<select  id="funcionarioFuncao" name="funcionario.funcao" >
							<option value="">Selecione</option>
							<c:forEach var="funcao" items="${funcoes }">
								<option value="${funcao.funcao_id }"> ${funcao.nome }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>

			<div class="btn-toolbar">
				<div class="btn-group">
					<button type="submit" class="btn btn-primary">Salvar</button>
				</div>	
			</div>

		</form>		
	</div>

<%@ include file="/footer.jspf"%>
