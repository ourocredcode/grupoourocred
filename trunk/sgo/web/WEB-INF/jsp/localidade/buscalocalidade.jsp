<div id="ajax_endereco" style="display: block;">

	<div class="row-fluid">
		<input class="span2" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
		<input class="span5 offset4" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
		<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número"/>
	</div>

	<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i>icon</i></div>

	<div class="row-fluid">		
		<input class="span2" id="parceirolocalidadeComplemento" name="parceirolocalidade.complemento" type="text" placeholder="Complemento" />
		<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
		<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
		<input class="span2" id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
		<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.nome }"  />
		<input class="span1" id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
		<input class="span1" id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
	</div>
	
	<div class="btn-toolbar">
		<div class="btn-group">
			<button type="button" class="btn btn-primary" id="bttLocalidade" onClick="salvaEndereco();">Adicionar Endereço</button>
		</div>	
	</div>

</div>