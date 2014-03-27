<%@ include file="/header.jspf"%>

	 <link rel="stylesheet" href="<c:url value="/css/select2.css"/>" />
	 <script type="text/javascript" src="<c:url value="/js/select2.js"/>"></script>

	<script type="text/javascript">
	jQuery(function($){

		   $('select').select2();
		
		   $("#localidadeCep").mask("99999999");
		   $("#parceiroNegocioCpf").mask("99999999999");
		   $("#parceiroNegocioDataNascimento").mask("99/99/9999");

		   $(".contactMask").mask("(99) 9999-9999?9");

		   $('#parceiroNegocioDataNascimento').datepicker();

		   $("#parceiroNegocioTipoParceiroId").change(function(evento){

			   var tipoParceiroId = document.getElementById("parceiroNegocioTipoParceiroId");
			   var tipoParceiroNome = tipoParceiroId.options[tipoParceiroId.selectedIndex].text;

			   if(tipoParceiroNome == 'Pessoa Física'){
				   $("#parceiroNegocioTipoPessoaFisica").css("display", "block");
			   } else {
				   $("#parceiroNegocioTipoPessoaFisica").css("display", "none");
			   }
			   
			   if(tipoParceiroNome == 'Pessoa Jurídica'){
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
				window.location.href = '<c:url value="/parceironegocio/limpar" />';
			});
		   
		   
		   $('#parceiroNegocioCpf').change(function () {

			   if((!$('#parceiroNegocioCpf').validateCPF()) && ($('#parceiroNegocioCpf').val() != '')){
				   alert("Cpf incorreto");
				   $('#parceiroNegocioCpf').val('');
				   $('#parceiroNegocioCpf').focus();
			   }

		   });
		   
		   $('#loading').ajaxStart(function() {
				 $(this).show();
				 $('#resultado').hide();
				 }).ajaxStop(function() {
				 $(this).hide();
				 $('#resultado').fadeIn('fast');
			});

	});

	function limpaForm(){
		if(!(navigator.userAgent.indexOf("Firefox") != -1)){
			document.usuarioForm.reset();
		}	
	}
	
	function novoHiscon(numeroBeneficio){

		var form = document.createElement("form");

		form.setAttribute("method", "post");
        form.setAttribute("action", "/sgo/hisconbeneficio/cadastro");

        var hiddenFieldEmpresa = document.createElement("input");

        hiddenFieldEmpresa.setAttribute("name", "empresa_id");
        hiddenFieldEmpresa.setAttribute("value",2 );
        hiddenFieldEmpresa.setAttribute("type", "hidden");

        var hiddenFieldOrganizacao = document.createElement("input");
        var parceiroNegocioOrganizacaoId = document.getElementById("parceiroNegocioOrganizacaoId").value;

        hiddenFieldOrganizacao.setAttribute("name", "organizacao_id");
        hiddenFieldOrganizacao.setAttribute("value",parceiroNegocioOrganizacaoId );
        hiddenFieldOrganizacao.setAttribute("type", "hidden");

        var hiddenFieldBeneficio = document.createElement("input");

        hiddenFieldBeneficio.setAttribute("name", "numeroBeneficio");
        hiddenFieldBeneficio.setAttribute("value",numeroBeneficio );
        hiddenFieldBeneficio.setAttribute("type", "hidden");

        form.appendChild(hiddenFieldEmpresa);
        form.appendChild(hiddenFieldOrganizacao);
        form.appendChild(hiddenFieldBeneficio);

        document.body.appendChild(form);

        form.submit();

	}
	
	function novoContrato(numeroBeneficio){

		var form = document.createElement("form");

		form.setAttribute("method", "post");
        form.setAttribute("action", "/sgo/formulario/cliente");
        
        var hiddenFieldBeneficio = document.createElement("input");

        hiddenFieldBeneficio.setAttribute("name", "numeroBeneficio");
        hiddenFieldBeneficio.setAttribute("value",numeroBeneficio );
        hiddenFieldBeneficio.setAttribute("type", "hidden");

        form.appendChild(hiddenFieldBeneficio);

        document.body.appendChild(form);

        form.submit();

	}
	
	function salvaLocalidade() {
	
		var cep = $("#localidadeCep").val();
		var endereco = $("#localidadeEndereco").val();
		var numero = $("#parceirolocalidadeNumero").val();
		var complemento = $("#parceirolocalidadeComplemento").val();
		var pontoreferencia = $("#parceirolocalidadePontoReferencia").val();
		var bairro = $("#localidadeBairro").val();
		var tipoLocalidadeId = $("#localidadeTipoLocalidadeId").val();
		var cidadeId = $("#localidadeCidadeId").val();
		var regiaoId = $("#localidadeRegiaoId").val();
		var parceiroId = $("#parceiroNegocioId").val();
		var localidadeId = $("#localidadeId").val();
		var paisId = $("#localidadePaisId").val();
	
		if (window.confirm("Deseja salvar o endereço?"))
			$.post('<c:url value='/parceironegocio/salvaLocalidade' />',{
					'localidade.cep' : cep ,
					'localidade.endereco' : endereco,
					'localidade.bairro' : bairro,
					'parceiroLocalidade.complemento' : complemento,
					'parceiroLocalidade.pontoReferencia' : pontoreferencia,
					'parceiroLocalidade.numero' : numero,
					'localidade.cidade.cidade_id' : cidadeId,
					'localidade.regiao.regiao_id' : regiaoId,
					'localidade.pais.pais_id' : paisId,
					'localidade.tipoLocalidade.tipoLocalidade_id' : tipoLocalidadeId,
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

		if(parceiroContatoNome == '' || parceiroContatoTipoContatoId == '') {

			alert("Complete com o tipo e o contato.");

		} else {
			
			if (window.confirm("Deseja salvar o contato?"))
				$.post('<c:url value='/parceironegocio/salvaContato' />',{
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
			
		}
		
	
		return false;
	
	}
	
	function salvaBeneficio() {

		var parceiroId = $("#parceiroNegocioId").val();
		var convenioId = $("#parceiroBeneficioConvenio").val();
		var numeroBeneficio = $("#parceiroBeneficioNumeroNovo").val();
		var senha = $("#parceiroBeneficioSenhaNovo").val();

		if(convenioId == '' || numeroBeneficio == '') {

			alert("Complete com o convênio e o benefício.");

		} else {

			if (window.confirm("Deseja salvar o número do benefício?"))
				$.post('<c:url value='/parceironegocio/salvaBeneficio' />',{
						'parceiroBeneficio.numeroBeneficio' : numeroBeneficio,
						'parceiroBeneficio.senha' : senha,
						'parceiroBeneficio.convenio.convenio_id' : convenioId,
						'parceiroBeneficio.parceiroNegocio.parceiroNegocio_id ' : parceiroId}
				, function(resposta) { 
					if(resposta.indexOf("Erro") != -1){
						alert(resposta);
					} else {
						$('#parceiroBeneficiosDiv').html(resposta);	
					};
				} );

		}

		return false;

	}
	
	function mostraEndereco() {
		
		 $("#ajax_endereco").css("display", "block");

		 $('button#bttLocalidade').text("Salvar").attr({
				title:"Salvar"
			});
	 
		 var mudar = document.getElementById('bttLocalidade');
		 mudar.setAttribute('onclick', 'salvaLocalidade()');
		
	}
	
	function cancelaEndereco() {
		
		 $("#ajax_endereco").css("display", "none");
		 
		 $('button#bttLocalidade').text("Novo").attr({
				title:"Novo"
			});
	
		 var mudar = document.getElementById('bttLocalidade');
		 mudar.setAttribute('onclick', 'mostraEndereco()');
		
	}
	
	function exclui(linha, id) {
	
		if (window.confirm("Deseja realmente excluir a Localidade do Parceiro ?"))
			$.post('<c:url value='/parceironegocio/excluiLocalidade' />'
			, {'parceiroLocalidade.parceiroLocalidade_id' : id}
			, function(resposta) { alert(resposta); excluiLinha(linha, resposta); });
	
		return false;
	}
	
	function excluiContato(linha, id) {
	
		if (window.confirm("Deseja realmente excluir o Contato do Parceiro ?"))
			$.post('<c:url value='/parceironegocio/excluiContato' />'
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

		if(atributo == 'isActive'){
			var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
					 'parceiroLocalidade.isActive' : valor };
		}

		if (window.confirm("Deseja realmente alterar o atributo do Parceiro Localidade?"))
			$.post('<c:url value='/parceironegocio/alteraParceiroLocalidade' />'
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
	
	function alteraBeneficio(linha, atributo,parceiroBeneficio_id,valor) {

		if(atributo == 'numeroBeneficio'){
			var attr = {'parceiroBeneficio.parceiroBeneficio_id' : parceiroBeneficio_id ,
					 'parceiroBeneficio.numeroBeneficio' : valor };
		}

		if(atributo == 'senha'){
			var attr = {'parceiroBeneficio.parceiroBeneficio_id' : parceiroBeneficio_id ,
					 'parceiroBeneficio.senha' : valor} ;
		}

		if(atributo == 'convenio.convenio_id'){
			var attr = {'parceiroBeneficio.parceiroBeneficio_id' : parceiroBeneficio_id ,
					 'parceiroBeneficio.convenio.convenio_id' : valor} ;
		}

		if (window.confirm("Deseja realmente alterar a Matrícula do Cliente?"))
			$.post('<c:url value='/parceironegocio/alteraParceiroBeneficio' />'
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
		<h1>Parceiro Negócio</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Parceiro Négocio</a>
	</div>
	

	<c:if test="${not empty notice}">
		<c:choose>
			<c:when test="${fn:contains(notice,'Erro:')}">
					<div class="alert alert-error">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:when>
			<c:when test="${fn:contains(notice,'Info:')}">
					<div class="alert alert-info">
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
	
	<c:if test="${parceiroNegocio.parceiroNegocio_id  == null }">
		<c:set var="url" value="/parceironegocio/salva"></c:set>
		<c:set var="descButton" value="Salvar"></c:set>
	</c:if>
	<c:if test="${parceiroNegocio.parceiroNegocio_id != null }">
		<c:set var="url" value="/parceironegocio/altera"></c:set>
		<c:set var="descButton" value="Alterar"></c:set>
	</c:if>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

					<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="${url }"/>" method="POST">

						<input id="parceiroNegocioId" name="parceiroNegocio.parceiroNegocio_id" value="${parceiroNegocio.parceiroNegocio_id }" type="hidden"/>
						<input id="funcionarioId" name="funcionario.funcionario_id" value="${funcionario.funcionario_id }" type="hidden"/>
						<input id="parceiroNegocioPnId" name="parceiroNegocio.pn_id" value="${parceiroNegocio.pn_id }" type="hidden"/>

						<div class="control-group">
							<div class="controls controls-row">
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsFuncionario" name="parceiroNegocio.isFuncionario" value="1"
									<c:if test="${parceiroNegocio.isFuncionario }">checked="checked"</c:if>
									<c:if test="${usuarioInfo.perfil.chave == 'Consultor' || usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Supervisor'}">disabled="disabled"</c:if>
									<c:if test="${usuarioInfo.perfil.chave == 'Gestor'}">checked="checked"</c:if>>Funcionário
								</label>
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsCliente" name="parceiroNegocio.isCliente" value="1"
									<c:if test="${parceiroNegocio.isCliente || usuarioInfo.perfil.chave == 'Consultor' || usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Supervisor' }">checked="checked"</c:if>> Cliente
								</label>
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsFornecedor" name="parceiroNegocio.isFornecedor" value="1"
									<c:if test="${parceiroNegocio.isFornecedor }">checked="checked"</c:if>
									<c:if test="${usuarioInfo.perfil.chave == 'Consultor' || usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Supervisor' }">disabled="disabled"</c:if>> Fornecedor
								</label>
							</div>
						</div>

						<br/>

						<div class="row-fluid">
							<div class="span2">
								<label for="parceiroNegocioEmpresa">Empresa</label>
								<input class="input-medium" id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly"/>
								<input class="span1" id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" />
							</div>
							<div class="span2">
								<label for="parceiroNegocioOrganizacao">Organização</label>
								<input  class="input-medium" id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required readonly="readonly" />
								<input  class="span1" id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" />
							</div>
							<div class="span2">
								<label for="categoriaParceiro">Categoria</label>
								<select class="input-medium" id="categoriaParceiro" name="parceiroNegocio.categoriaParceiro.categoriaParceiro_id">
									<!--option value="">Escolha uma banco</option-->
									<c:forEach items="${categoriasParceiro }" var="categoriaParceiro">
										<option value="${categoriaParceiro.categoriaParceiro_id}"> ${categoriaParceiro.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="classificacaoParceiro">Classificação</label>
								<select class="input-medium" id="classificacaoParceiro" name="parceiroNegocio.classificacaoParceiro.classificacaoParceiro_id">
									<!--option value="">Escolha uma banco</option-->
									<c:forEach items="${classificacoesParceiro }" var="classificacaoParceiro">
										<option value="${classificacaoParceiro.classificacaoParceiro_id}"> ${classificacaoParceiro.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="grupoParceiro">Grupo</label>
								<select class="input-medium" id="grupoParceiro" name="parceiroNegocio.grupoParceiro.grupoParceiro_id">
									<!--option value="">Escolha uma banco</option-->
									<c:forEach items="${gruposParceiro }" var="grupoParceiro">
										<option value="${grupoParceiro.grupoParceiro_id}"> ${grupoParceiro.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>	

						<div class="control-group"></div>

						<div class="controls controls-row">
							<label for="parceiroNegocioTipoParceiroId">Tipo Parceiro</label>
							<select id="parceiroNegocioTipoParceiroId" name="parceiroNegocio.tipoParceiro.tipoParceiro_id" class="input-medium">
								<c:forEach var="tipoParceiro" items="${tiposParceiro }">
								 	<option value="${tipoParceiro.tipoParceiro_id }" <c:if test="${parceiroNegocio.tipoParceiro.tipoParceiro_id eq tipoParceiro.tipoParceiro_id }"> selected="selected"</c:if>>${tipoParceiro.nome }</option>
								</c:forEach>
							</select>
						</div>

						<div class="control-group"></div>
				
						<div class="controls controls-row">
							<label for="parceiroNegocioNome">Nome</label>
							<input class="input-xxlarge" id="parceiroNegocioNome" name="parceiroNegocio.nome" type="text" placeholder="Nome" value="${parceiroNegocio.nome }" required>				
						</div>
						
						<div id="parceiroNegocioTipoPessoaFisica" class="row-fluid" 

								<c:if test="${parceiroNegocio.isFuncionario || parceiroNegocio.isCliente || empty parceiroNegocio }">style="display: block;"</c:if>
								<c:if test="${parceiroNegocio.isFornecedor}">style="display: none;"</c:if>>

								<div class="span2">
									<label for="parceiroNegocioCpf">CPF</label>
									<input  class="input-medium" id="parceiroNegocioCpf" name="parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${parceiroNegocio.cpf }" required>
								</div>
								<div class="span2">
									<label for="parceiroNegocioRg">RG</label>
									<input  class="input-medium" id="parceiroNegocioRg" name="parceiroNegocio.rg" type="text" placeholder="Rg" value="${parceiroNegocio.rg }">
								</div>
								<div class="span2">
									<label for="parceiroNegocioDataNascimento">Dt Nascimento</label>
									<input  class="input-medium" id="parceiroNegocioDataNascimento" name="parceiroNegocio.dataNascimento" type="text" placeholder="Nasc." 
									value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${parceiroNegocio.dataNascimento.time }" />">
								</div>
								<div class="span2">
									<label for="parceiroNegocioSexoId">Sexo</label>
									<select  id="parceiroNegocioSexoId" name="parceiroNegocio.sexo.sexo_id" class="input-medium" required>
										<c:forEach var="sexo" items="${sexos }">
										 	<option value="${sexo.sexo_id }" <c:if test="${parceiroNegocio.sexo.sexo_id eq sexo.sexo_id }"> selected="selected"</c:if>>${sexo.nome }</option>
										</c:forEach>
									</select>
								</div>
								<div class="span2">
									<label for="parceiroNegocioEstadoCivilId">Estado Civil</label>
									<select  id="parceiroNegocioEstadoCivilId" name="parceiroNegocio.estadoCivil.estadoCivil_id" class="input-medium" required>										
										<c:forEach var="estadoCivil" items="${estadosCivis }">
										 	<option value="${estadoCivil.estadoCivil_id }" <c:if test="${parceiroNegocio.estadoCivil.estadoCivil_id eq estadoCivil.estadoCivil_id }"> selected="selected"</c:if>>${estadoCivil.nome }</option>
										</c:forEach>
									</select>
								</div>
			
							</div>
				
							<div id="parceiroNegocioTipoPessoaJuridica" class="row-fluid" style="display: none;">
								<div class="span2">
									<label for="parceiroNegocioCnpj">CNPJ</label>
									<input  class="input-medium" id="parceiroNegocioCnpj" name="parceiroNegocio.cnpj" type="text" placeholder="Cnpj">
								</div>
								<div class="span2">
									<label for="parceiroNegocioIe">Inscrição Estadual</label>
									<input  class="input-medium" id="parceiroNegocioIe" name="parceiroNegocio.ie" type="text" placeholder="Insc Estadual">
								</div>
							</div>
				
						<div id="parceiroNegocioFuncionario" class="row-fluid"
								<c:if test="${usuarioInfo.perfil.chave == 'Supervisor' ||  usuarioInfo.perfil.chave == 'Gestor'}">style="display: block;"</c:if>
								<c:if test="${parceiroNegocio.isFuncionario eq false }">style="display: none;"</c:if>
								<c:if test="${parceiroNegocio.isCliente || parceiroNegocio.isFornecedor ||  empty parceiroNegocio}">style="display: none;"</c:if>>
				
							<div class="controls controls-row">
								
								<div class="span2">
									<label for="funcionarioDepartamentoId">Departamento</label>
									<select  id="funcionarioDepartamentoId" name="funcionario.departamento.departamento_id" class="input-medium">
										<c:forEach var="departamento" items="${departamentos }">
											<option value="${departamento.departamento_id }" <c:if test="${funcionario.departamento.departamento_id eq departamento.departamento_id }"> selected="selected"</c:if>> ${departamento.nome }</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="span2">
									<label for="funcionarioOperacaoId">Operação</label>
									<select  id="funcionarioOperacaoId" name="funcionario.operacao.operacao_id" class="input-medium">
										<option value=""></option>
										<c:forEach var="operacao" items="${operacoes }">
											<option value="${operacao.operacao_id }" <c:if test="${funcionario.operacao.operacao_id eq operacao.operacao_id }"> selected="selected"</c:if>> ${operacao.nome }</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="span2">
									<label for="funcionarioFuncaoId">Função</label>
									<select  id="funcionarioFuncaoId" name="funcionario.funcao.funcao_id" class="input-medium">
										<c:forEach var="funcao" items="${funcoes }">
											<option value="${funcao.funcao_id }" <c:if test="${funcionario.funcao.funcao_id eq funcao.funcao_id }"> selected="selected"</c:if>> ${funcao.nome }</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="span2">
									<label for="funcionarioSupervisorId">Supervisor</label>
									<select  id="funcionarioSupervisorId" name="funcionario.supervisorFuncionario.funcionario_id" class="input-medium">
										<option value="">Selecione o supervisor</option>
										<c:forEach var="supervisor" items="${supervisores }">
											 <option value="${supervisor.funcionario_id }" <c:if test="${funcionario.supervisorFuncionario.funcionario_id eq supervisor.funcionario_id }"> selected="selected"</c:if>> ${supervisor.nome }</option>
										</c:forEach>										
									</select>
								</div>
								
								<div class="span2">
									<label for="funcionarioApelido">Codinome</label>
									<input id="funcionarioApelido" name="funcionario.apelido" type="text" placeholder="Codinome" value="${funcionario.apelido }" class="input-medium">
								</div>
								
				
							</div>
						</div>
				
						<br/>
						<div class="row-fluid">
							<c:if test="${not empty parceiroInfoBanco}">
								<div class="navbar" style="float: left;">
									
								<div class="navbar-inner" >
								
									<div class="container">
										<div class="control-group"></div>
												
											<div id="parceiroMeioPagamentoDiv">	
												<table class="table table-striped table-bordered" id="lista">
													<thead>
														<tr>
															<th>Banco</th>
															<th>Conta</th>
															<th>Agencia</th>
															<th>Meio Pagamento</th>
														</tr>
													</thead>
													<tbody>	
														<tr>
															<td>
																<select id="parceiroInfoBancoId" name="parceiroInfoBanco.banco.banco_id" class="input-small" >
																	<option value="">Selecione o banco...</option>
																	<c:forEach var="banco" items="${bancos }">
																		<option value="${banco.banco_id }" <c:if test="${parceiroInfoBanco.banco.banco_id == banco.banco_id }">selected="selected"</c:if>>${banco.nome }</option>
																	</c:forEach>
																</select>
															</td>
															<td><input type="text" id="parceiroInfoBancoContacorrente" name="parceiroInfoBanco.contaCorrente" value="${parceiroInfoBanco.contaCorrente }" class="input-small" /></td>
															<td><input type="text" id="parceiroInfoBancoAgenciaNumero" name="parceiroInfoBanco.agenciaNumero" value="${parceiroInfoBanco.agenciaNumero }" class="input-small" /></td>
															<td>
																<select id="parceiroInfoMeioPagamentoId" name="parceiroInfoBanco.meioPagamento.meioPagamento_id" class="input-small" >
																	<option value="">Selecione o banco...</option>
																	<c:forEach var="meioPagamento" items="${meiosPagamento }">
																		<option value="${meioPagamento.meioPagamento_id }" <c:if test="${parceiroInfoBanco.meioPagamento.meioPagamento_id == meioPagamento.meioPagamento_id }">selected="selected"</c:if>>${meioPagamento.nome }</option>
																	</c:forEach>
																</select>
															</td>
															
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
						<div class="row-fluid">
							<c:if test="${ empty parceiroContatos && not empty parceiroNegocio.parceiroNegocio_id && parceiroNegocio.isCliente == true}">	
								<div class="navbar" style="width: 350px;float: left;">
									
									<div class="navbar-inner" >
								
										<div class="container">
										
											<div class="control-group">
											</div>
											
											<div id="parceiroContatosDiv">	
													
												<table class="table table-striped table-bordered" id="lista">
												<thead>
													<tr>
														<th>TipoContato</th>
														<th>Contato</th>
														<th>Excluir</th>
													</tr>
													<tr>
														<td>
															<select id="parceiroContatoTipoContatoNovo" class="input-small" required>
																<c:forEach var="tipoContato" items="${tiposContato}">
																	<option value="${tipoContato.tipoContato_id}" >${tipoContato.chave}</option>
																</c:forEach>
															</select>
														</td>
														<td><input type="text" id="parceiroContatoNomeNovo" value="${parceiroContato.nome }" class="input-small contactMask" required /></td>
														<td style="text-align: center;">
															<button type="button" class="btn btn-mini" id="bttParceiroContatoNovo" onClick="return salvaContato();">Novo</button>
														</td>
													</tr>
												</thead>
												</table>
											</div>
										</div>
									</div>
								</div>				

							</c:if>
						
							<c:if test="${not empty parceiroContatos}">	
								<div class="navbar" style="width: 350px;float: left;">
									
									<div class="navbar-inner" >
								
										<div class="container">
										
											<div class="control-group">
											</div>
											
											<div id="parceiroContatosDiv">	
													
												<table class="table table-striped table-bordered" id="lista">
												<thead>
													<tr>
														<th>TipoContato</th>
														<th>Contato</th>
														<th>Excluir</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${parceiroContatos}" var="parceiroContato" varStatus="status">
														<tr>
															<td>
																<select id="parceiroContatoTipoContatoLista"  name="parceiroContatos[${status.index}].tipoContato.tipoContato_id" onChange="return alteraContato(this,'tipoContato','${parceiroContato.parceiroContato_id}', this.value);" class="input-small">
																	<option value="0" selected="selected">Selecione</option>
																	<c:forEach var="tipoContato" items="${tiposContato}">
																		<option value="${tipoContato.tipoContato_id}" <c:if test="${parceiroContato.tipoContato.tipoContato_id eq tipoContato.tipoContato_id}">SELECTED</c:if>>${tipoContato.chave}</option>
																	</c:forEach>
																</select>
															</td>
															<td><input type="text" id="parceiroContatoNomeLista" name="parceiroContatos[${status.index}].nome" value="${parceiroContato.nome }" class="input-small" onChange="return alteraContato(this,'nome','${parceiroContato.parceiroContato_id}', this.value);"/></td>
															<td style="text-align: center;">
																<button type="button" class="btn btn-danger btn-mini" onClick="return excluiContato(this,'${parceiroContato.parceiroContato_id}');">Excluir</button>
															</td>
														</tr>
													</c:forEach>
													<c:if test="${not empty parceiroNegocio.parceiroNegocio_id}">
														<tr>
															<td>
																<select id="parceiroContatoTipoContatoNovo" class="input-small" required>
																	<c:forEach var="tipoContato" items="${tiposContato}">
																		<option value="${tipoContato.tipoContato_id}" >${tipoContato.chave}</option>
																	</c:forEach>
																</select>
															</td>
															<td><input type="text" id="parceiroContatoNomeNovo" value="${parceiroContato.nome }" class="input-small contactMask" required /></td>
															<td style="text-align: center;">
																<button type="button" class="btn btn-mini" id="bttParceiroContatoNovo" onClick="return salvaContato();">Novo</button>
															</td>
														</tr>
													</c:if>
												</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</c:if>
	
							<c:if test="${empty parceiroBeneficios && not empty parceiroNegocio.parceiroNegocio_id && parceiroNegocio.isCliente == true}">
								
								<div class="navbar" style="width: 650px;float: left;">
									
								<div class="navbar-inner" >
								
									<div class="container">
										<div class="control-group"></div>
												
											<div id="parceiroBeneficiosDiv">	
												<table class="table table-striped table-bordered" id="lista">
													<thead>
														<tr>
															<th>Convênio</th>
															<th>Matrícula</th>
															<th>Senha</th>
															<c:if test="${parceiroNegocio.parceiroNegocio_id  != null }">
																<th>Hiscon</th>
																<th>Contrato</th>
															</c:if>
														</tr>
														<c:if test="${not empty parceiroNegocio.parceiroNegocio_id}">
															<tr>
																<td>
																	<select id="parceiroBeneficioConvenio" name="parceiroBeneficioConvenio" >
																		<c:forEach var="convenio" items="${convenios }">
																			<option value="${convenio.convenio_id }">${convenio.nome }</option>
																		</c:forEach>
																	</select>
																</td>
																<td><input type="text" id="parceiroBeneficioNumeroNovo" value="${parceiroBeneficio.numeroBeneficio }" class="input-small" /></td>
																<td><input type="text" id="parceiroBeneficioSenhaNovo" value="${parceiroBeneficio.senha }" class="input-small" /></td>
																<td style="text-align: center;">
																	<button type="button" class="btn btn-mini" id="bttParceiroBeneficioNovo" onClick="return salvaBeneficio();">Novo</button>
																</td>
															</tr>
														</c:if>
													</thead>
												</table>
											</div>
									</div>
								</div>
								</div>					

							</c:if>
	
							<c:if test="${not empty parceiroBeneficios}">
								<div class="navbar" style="width: 650px;float: left;">
									
								<div class="navbar-inner" >
								
									<div class="container">
										<div class="control-group"></div>
												
											<div id="parceiroBeneficiosDiv">	
												<table class="table table-striped table-bordered" id="lista">
													<thead>
														<tr>
															<th>Convênio</th>
															<th>Matrícula</th>
															<th>Senha</th>
															<c:if test="${parceiroNegocio.parceiroNegocio_id  != null }">
																<th>Hiscon</th>
																<th>Contrato</th>
															</c:if>
														</tr>
													</thead>
													<tbody>	
														<c:forEach items="${parceiroBeneficios}" var="parceiroBeneficio" varStatus="status">
															<tr>
																<td>
																<select type="text" id="parceiroConvenioNome" name="parceiroBeneficios[${status.index}].convenio.convenio_id"  class="input-small" onChange="return alteraBeneficioDESATIVADO(this,'convenio.convenio_id','${parceiroBeneficio.parceiroBeneficio_id }', this.value);">
																	<c:forEach items="${convenios }" var="convenio">
																		<option value="${convenio.convenio_id }" <c:if test="${parceiroBeneficio.convenio.convenio_id eq convenio.convenio_id}">selected="selected"</c:if>>${convenio.nome }</option>
																	</c:forEach>
																</select>
																</td>
																<td><input type="text" id="parceiroBeneficioNumeroLista" name="parceiroBeneficios[${status.index}].numeroBeneficio" value="${parceiroBeneficio.numeroBeneficio }" class="input-small" onChange="return alteraBeneficioDESATIVADO(this,'numeroBeneficio','${parceiroBeneficio.parceiroBeneficio_id}', this.value);" readonly="readonly"/></td>
																<td><input type="text" id="parceiroBeneficioSenhaLista" name="parceiroBeneficios[${status.index}].senha" value="${parceiroBeneficio.senha }" class="input-small" onChange="return alteraBeneficio(this,'senha','${parceiroBeneficio.parceiroBeneficio_id}', this.value);"/></td>															
																<c:if test="${parceiroNegocio.parceiroNegocio_id  != null }">
																	<td style="text-align: center;">
																		
																		<div class="buttons">
																			<a href="#" class="btn btn-mini" onclick="novoHiscon('${parceiroBeneficio.numeroBeneficio}');"><i class="icon-search"></i> Hiscon </a>
																		</div>
																		
																	</td>
																	<td style="text-align: center;">
																	
																		<div class="buttons">
																			<a href="#" class="btn btn-mini" onclick="novoContrato('${parceiroBeneficio.numeroBeneficio}');"><i class="icon-file"></i> Contrato </a>
																		</div>
	
																	</td>
																</c:if>
															</tr>
														</c:forEach>
														<c:if test="${not empty parceiroNegocio.parceiroNegocio_id}">
															<tr>
																<td>
																	<select id="parceiroBeneficioConvenio" name="parceiroBeneficioConvenio" >
																		<c:forEach var="convenio" items="${convenios }">
																			<option value="${convenio.convenio_id }">${convenio.nome }</option>
																		</c:forEach>
																	</select>
																</td>
																<td><input type="text" id="parceiroBeneficioNumeroNovo" value="${parceiroBeneficio.numeroBeneficio }" class="input-small" /></td>
																<td><input type="text" id="parceiroBeneficioSenhaNovo" value="${parceiroBeneficio.senha }" class="input-small" /></td>
																<td style="text-align: center;">
																	<button type="button" class="btn btn-mini" id="bttParceiroBeneficioNovo" onClick="return salvaBeneficio();">Novo</button>
																</td>
															</tr>
														</c:if>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
						<c:if test="${not empty parceiroLocalidades}">
							<div class="navbar" style="clear: both;width: 900px;">
				
								<div class="navbar-inner"  >
									<div class="container">
				
										<div class="page-header">
											<h2><small>Endereços</small></h2>
										</div>
				
										<div id="enderecos" style="margin-top: 15px;">
											<table class="table table-striped table-bordered" id="lista">
												<thead>
													<tr>
														<th>Ativo</th>
														<th>Cep</th>
														<th>Bairro</th>
														<th>Cidade</th>
														<th>Tipo</th>
														<th>Endereço</th>
														<th>Número</th>
														<th>Complemento</th>
														<th>Tipo</th>
														<!-- 
														<th>Excluir</th>
														 -->
													</tr>
												</thead>
												<tbody>	
													<c:forEach items="${parceiroLocalidades}" var="parceiroLocalidade">
														<tr>
															<td>
																<input type="checkbox" id="parceiroLocalidadeIsActive" name="parceiroNegocio.isActive" value="${parceiroNegocio.isActive }" <c:if test="${parceiroLocalidade.isActive}">checked="checked"</c:if> onChange="return altera(this,'isActive','${parceiroLocalidade.parceiroLocalidade_id}', this.checked);">
															</td>
															<td>${parceiroLocalidade.localidade.cep }</td>
															<td>${parceiroLocalidade.localidade.bairro }</td>
															<td>${parceiroLocalidade.localidade.cidade.nome }</td>
															<td>${parceiroLocalidade.localidade.tipoLocalidade.nome }</td>
															<td>${parceiroLocalidade.localidade.endereco }</td>
															<td><input type="text" id="parceiroLocalidadeNumeroLista" value="${parceiroLocalidade.numero }" class="input-small" onChange="return altera(this,'numero','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
															<td><input type="text" id="parceiroLocalidadeComplementoLista" value="${parceiroLocalidade.complemento }" class="input-medium" onChange="return altera(this,'complemento','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
															<td>${parceiroLocalidade.tipoEndereco.nome }</td>
															<!-- 
															<td>
															<select id="parceiroLocalidadeTipoEnderecoLista" onChange="return alteraDESATIVADO(this,'tipoEndereco','${parceiroLocalidade.parceiroLocalidade_id}', this.value);" class="input-small">
																<option value="0" selected="selected">Selecione</option>
																	<c:forEach var="tipoEndereco" items="${tiposEndereco}">
																		<option value="${tipoEndereco.tipoEndereco_id}" <c:if test="${parceiroLocalidade.tipoEndereco.tipoEndereco_id eq tipoEndereco.tipoEndereco_id}">SELECTED</c:if>>${tipoEndereco.nome}</option>
																	</c:forEach>
															</select>
															</td>
															
															<td style="text-align: center;">
																<button type="button" class="btn btn-danger btn-mini" onClick="return excluiDESATIVADO(this,'${parceiroLocalidade.parceiroLocalidade_id}');">Excluir</button>
															</td>
															 -->
														</tr>
													</c:forEach>
												</tbody>
											</table>
				
										</div>
									</div>
								</div>										
						</div>
					</c:if>
				
					<div class="navbar" style="clear: both;width: 920px;">
							<div class="navbar-inner">
								<div class="container">
								
								<div class="control-group"></div>
									<div class="page-header">
										<h2><small>Endereço</small></h2>
									</div>
								
								<div id="ajax_endereco" <c:if test="${not empty parceiroNegocio && !parceiroNegocio.isCliente}">style="display: none;"</c:if>>
				
									
									<div class="row-fluid">
										<div class="span3">
											<div class="input-append">
												<input class="span10" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
												<span class="add-on"><i class="icon-search"></i></span>
											</div>
										</div>
										<div class="span2">
											<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:right;clear: both;margin-left: 600px;margin-top: 1px;">BUSCANDO...</div>
										</div>
									</div>
				
									<div class="row-fluid">
										<input class="span2" id="localidadeTipoLocalidade" name="localidade.tipoLocalidade.nome" type="text" placeholder="Tipo" value="${localidade.tipoLocalidade.nome }"/>
										<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
										<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número" value="${parceiroLocalidade.numero }" />
										<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
										<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
										<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
									</div>
				
									<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>
				
									<div class="row-fluid">		
				
										<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" value="${parceiroLocalidade.complemento }" />
										<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Referência" value="${parceiroLocalidade.pontoReferencia }" />
				
										<input  id="localidadeTipoLocalidadeId" name="localidade.tipoLocalidade.tipoLocalidade_id" type="hidden"  value="${localidade.tipoLocalidade.tipoLocalidade_id }" />
										<input  id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
										<input  id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
										<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
								
									</div>
				
								</div>
								
								<c:if test="${not empty parceiroNegocio }">
									<div class="btn-toolbar" align="right">
										<div class="row-fluid">
											<div class="btn-group">
												<button type="button" class="btn btn-primary" id="bttLocalidade" onClick="mostraEndereco();">Novo</button>
											</div>	
											<div class="btn-group">
												<button type="button" class="btn" id="bttCancelar" onClick="cancelaEndereco();">Fechar</button>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>

					<div class="controls controls-row">
						<label class="checkbox">
							<input type="checkbox" id="parceiroNegocioIsActive" name="parceiroNegocio.isActive" checked="checked" value="1"> Ativo
						</label>							
					</div>

					<div class="btn-toolbar">
						<div class="btn-group">
							<c:if test="${( usuarioInfo.perfil.chave == 'Gestor' || usuarioInfo.perfil.chave == 'Administrador' ) && (parceiroNegocio.parceiroNegocio_id  != null)}">
								<button type="submit" class="btn btn-primary">${descButton }</button>
							</c:if>
							<c:if test="${parceiroNegocio.parceiroNegocio_id  == null }">
								<button type="submit" class="btn btn-primary">${descButton }</button>
							</c:if>
						</div>		
					</div>
				</form>		

				<div class="btn-group">
					<form action="<c:url value="/parceironegocio/limpar"/>" method="POST" >
						<input type="submit" value="Limpar" class="btn"/>
					</form>
				</div>	

				</div>
			</div>	
		</div>
		

<%@ include file="/footer.jspf"%>
