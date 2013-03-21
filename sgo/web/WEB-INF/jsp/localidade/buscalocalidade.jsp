<div class="controls controls-row">
	<input class="span2" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }"/>
</div>
<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 180px;margin-top: -27px;"><i>Digite para buscar</i></div>

<div class="controls controls-row">
	<input class="span7" id="localidadeEndereco" name="localidade.endereco" type="text"  value="${localidade.endereco }"/>
	<input class="span1" id="parceiroLocalidadeNumero" name="parceiroLocalidade.numero" type="text" />
</div>

<div class="controls controls-row">
	<input class="span3" id="parceiroLocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" />
	<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
	<input class="span2" id="localidadeCidadeNome" name="localidade.cidade.nome" type="text" placeholder="Cidade"  value="${localidade.cidade.nome }"/>
	<input class="span2" id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }"/>
	<input class="span1" id="localidadeRegiaoNome" name="localidade.regiao.nome" type="text" placeholder="UF"  value="${localidade.regiao.nome }" />
	<input class="span1" id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" placeholder="UF"  value="${localidade.regiao.regiao_id }" />
	<input class="span1" id="localidadePaisId" name="localidade.pais.pais_id" type="hidden" placeholder="UF"  value="${localidade.pais.pais_id }" />
</div>