<div class="controls controls-row">
	<input class="span2" id="localidadeCep" name="localidadeCep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
</div>
<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 180px;margin-top: -27px;"><i>Digite para buscar</i></div>

<div class="controls controls-row">
	<input class="span7" id="localidadeEndereco" name="localidadeEndereco" type="text" placeholder="Endereço" value="${localidade.endereco1 }"/>
	<input class="span1" id="localidadeNumero" name="localidadeNumero" type="text" placeholder="Número"/>
</div>

<div class="controls controls-row">
	<input class="span3" id="localidadeComplemento" name="localidadeComplemento" type="text" placeholder="Complemento" />
	<input class="span2" id="localidadeBairro" name="localidadeBairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
	<input class="span2" id="localidadeCidade" name="localidadeCidade" type="text" placeholder="Cidade"  value="${localidade.cidade.nome }"/>
	<input class="span1" id="localidadeRegiao" name="localidadeRegiao" type="text" placeholder="UF" value="${localidade.regiao.nome }" />
</div>