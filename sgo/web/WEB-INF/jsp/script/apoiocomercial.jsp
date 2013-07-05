<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bancred").style.display = "none";
	document.getElementById("banrisul").style.display = "none";
	document.getElementById("itau").style.display = "none";
	document.getElementById("matone").style.display = "none";
	document.getElementById("santander").style.display = "none";
	document.getElementById("sofisa").style.display = "none";
	document.getElementById("unibanco").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">Escolha a opção abaixo:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bancred');">Bancred</a></li>
	<li><a href="#" onclick="mostra('banrisul');">Banrisul</a></li>
 	<li><a href="#" onclick="mostra('itau');">Itaú</a></li>
 	<li><a href="#" onclick="mostra('matone');">Matone</a></li>
 	<li><a href="#" onclick="mostra('santander');">Santander</a></li>
 	<li><a href="#" onclick="mostra('sofisa');">Sofisa</a></li>
 	<li><a href="#" onclick="mostra('unibanco');">Unibanco</a></li>
</ul>
</div>

<div id="bancred" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Bancred</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação.</td>
		</tr>
	</table>

</div>


<div id="banrisul" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Banrisul</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Enviar email para o banco com RG, CPF e Comprovante de Endereço, aguardar o prazo de 20 dias para a chegada do boleto</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Controle de averbação</td>
		</tr>
	</table>

</div>



<div id="itau" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Itaú</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
	</table>
</div>

<div id="matone" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Matone / Original</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
	</table>
</div>

<div id="santander" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Santander</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th >Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
	</table>
</div>

<div id="sofisa" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Sofisa</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
	</table>
</div>

<div id="unibanco" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Unibanco</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Telefone</td>
			<td>Solicitação junto a central de consignado da Instituição</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato no departamento vamos entrar em contato com o Banco fazendo a solicitação do boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Enviar o boleto para quitação</td>
		</tr>
	</table>
</div>

<%@ include file="/footer.jspf" %> 