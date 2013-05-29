<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("clienteAusente").style.display = "none";
	document.getElementById("clienteDeficienciaElevada").style.display = "none";
	document.getElementById("clienteDeficienciaElevadaProcuracao").style.display = "none";
	document.getElementById("clientePresente").style.display = "none";
	document.getElementById("finalizacaoVenda").style.display = "none";
	document.getElementById("reembolsoParcela").style.display = "none";
	
	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">Escolha o Script Abaixo:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('clienteAusente');">Cliente ausente</a></li>
 	<li><a href="#" onclick="mostra('clienteDeficienciaElevada');">Cliente presente – Grau de deficiência elevado</a></li>
 	<li><a href="#" onclick="mostra('clienteDeficienciaElevadaProcuracao');">Cliente presente – Grau de deficiência elevado (procuração)</a></li>
 	<li><a href="#" onclick="mostra('clientePresente');">Cliente presente</a></li>
 	<li><a href="#" onclick="mostra('finalizacaoVenda');">Finalização venda</a></li>
 	<li><a href="#" onclick="mostra('reembolsoParcela');">Discurso reembolso de parcela</a></li>
</ul>
</div>

<div id="clienteAusente" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Cliente Ausente</h2><br/>
<b>Consultor: Bom dia/ Boa tarde,</b><br/>
<b>Consultor: Por gentileza o (a) senhor (a) __________________.</b> <br/>
<br/>
Morador: Quem gostaria?<br/>
<br/>
<b>Consultor: Meu nome é __________, eu sou consultor de negócios do Banco _________.</b><br/>
<br/>
Morador: O senhor (a) __________ não está no momento, eu sou esposa, filho, filha, sobrinho, enteada..., pode tratar comigo mesmo (a).<br/>
<br/>
<b>Consultor: Senhor (a), obrigada pela disposição em me atender, mas sou autorizado (a) a passar o assunto somente para o beneficiário. O senhor (a) poderia, por gentileza, anotar um número e pedir para ele me retornar?</b><br/> 
<b>Se caso ele (a) tiver alguma dificuldade física que dificulte nosso contato, o senhor (a) poderá em algum momento intermediar nossa conversa, porém, o primeiro contato, para própria segurança do senhor (a), é necessário tratar diretamente com ele (a) e ele me autorizar. Agradeço sua atenção!</b><br/>

</div>

<div id="clienteDeficienciaElevada" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Cliente Presente - Grau de Deficiência Elevado</h2><br/>
<b>Consultor: Bom dia/ Boa tarde,</b><br/>
<b>Consultor: Por gentileza o (a) senhor (a) __________________.</b><br/>
<br/>
Morador: Quem gostaria?<br/>
<br/>
<b>Consultor: Meu nome é __________, eu sou consultor de negócios do Banco _________.</b><br/>
Morador: O senhor (a) __________ não pode falar por conta de uma dificuldade física. Eu sou esposa, filho, filha, sobrinho, enteada..., pode tratar comigo mesmo (a).<br/> 
<br/>
<b>Consultor: Se me permite perguntar, qual a dificuldade que ele tem?¹</b><br/>
<br/><br/>
<b>(CASO A DIFICULDADE DO CLIENTE O IMPEÇA DE MANTER CONTATO, OU SE ENQUADRE EM DEFICIÊNCIA MENTAL, A LIGAÇÃO DEVE SER ENCERRADA)<br/>
 Finalize a ligação</b><br/>
<br/>
<b>Consultor: Senhor (a), obrigada pela disposição em me atender, mas sou autorizado (a) a passar o assunto somente para o beneficiário. Desculpa-me, mas como se trata de informações de sigilo bancário, o que tenho para oferecer deve ser tratado somente com o beneficiário mesmo. Agradeço a atenção.</b><br/> 
<br/>
¹A deficiência física do cliente, deve existir em um grau que não o impeça de: <br/>
 - Autorizar que uma terceira pessoa o interceda na negociação;<br/>
 - Entender que a negociação feita trata-se de um empréstimo e<br/>
 - Entender e concordar com os dados do contrato, inclusive os débitos no benefício, autorizados por ele mediante assinatura da proposta.<br/>  

</div>

<div id="clienteDeficienciaElevadaProcuracao" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Cliente Presente - Grau de Deficiência Elevado Procuração</h2><br/>
<b>Consultor: Bom dia/ Boa tarde,</b><br/>
<b>Consultor: Por gentileza o (a) senhor (a) __________________.</b><br/>
<br/>
Morador: Quem gostaria?<br/>
<br/>
<b>Consultor: Meu nome é __________, eu sou consultor de negócios do Banco _________.</b><br/>
Morador: O senhor (a) __________ não pode falar por conta de uma dificuldade física. Eu sou esposa, filho, filha, sobrinho, enteada e possuo a procuração² dele..., pode tratar comigo mesmo (a).<br/> 
<br/>
<b>Finalize a ligação</b><br/>
<br/>
<b>Consultor: Senhor (a), obrigada pela disposição em me atender, mas como tratamos de liberação de crédito, o banco não autoriza contratos feitos por procuração ou tutela. Obrigada pela atenção.</b><br/> 
<br/>
² Benefícios que são recebidos por meio de representante legal: dependente, tutelado ou curatelado; NÃO poderão ser utilizados na realização de consignação, em hipótese alguma.<br/> 

</div>

<div id="clientePresente" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Cliente Presente</h2><br/>
<b>Consultor: Bom dia/ Boa tarde,</b><br/>
<b>Consultor: Por gentileza o (a) senhor (a) __________________.</b><br/>
<br/>
Morador: Quem gostaria?<br/>
<br/>
<b>Consultor: Meu nome é __________, eu sou consultor de negócios do Banco _________.</b><br/>
<br/>
Morador: Eu sou esposa, filho, filha, sobrinho, enteada..., pode tratar comigo mesmo (a), ele tem uma dificuldade física e fica difícil falar ao telefone.<br/> 
<br/>
<b>Consultor: O (a) senhor (a) pode me ajudar bastante, mas neste caso eu preciso ao menos explicar a ele (a) o motivo da minha ligação e pedir para autorização para conversar para as informações para o (a) senhor (a). O (a) senhor (a) pode chamá-lo (a), por favor?</b><br/> 
<br/>
Inicie a conversa com o (a) cliente, explique o motivo da ligação, informe que você tratará de assuntos referente a empréstimo consignado e se ele (a) autoriza que essas informações sejam intermediadas pelo (a) (FALE O NOME DA PESSOA).<br/> 
Neste momento você deverá ouvir SIM do cliente, não somente expressões como “ahã”ou “hum”... Reforce ao cliente que as informações de empréstimo que existem no benefício dele, serão transmitidas a essa terceira pessoa (NOVAMENTE CITE O NOME DA PESSOA) e aguarde o aceite por parte dele.<br/>
<br/>
<br/>
Após autorização do cliente: Inicie a abordagem e ofereça normalmente o crédito consignado a essa terceira pessoa. Após passar as informações solicite que sejam repassadas ao cliente e aguarde.<br/> 
Havendo interesse, solicite novamente ao cliente para atender ao telefone, explique o que foi oferecido, pergunte se ele entendeu o que lhe foi passado e aguarde o aceite dele.<br/> 
O aceite não deve ser feito por ninguém além do próprio beneficiário. A pessoa intermediária será apenas uma “ponte” para transmitir informações entre você e o cliente.<br/>

<br/>
<b>LEMBRANDO QUE A DEFICIÊNCIA CONSIDERADA É FÍSICA E NÃO MENTAL.</b><br/>

</div>

<div id="finalizacaoVenda" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Finalização Venda</h2><br/>
Senhor(a), apenas para formalizarmos nosso contato, farei uma breve confirmação para sua segurança. <br/>
Peço se possível, anote para sua consulta, pois quando o setor de controle de qualidade entrar em contato para saber se o senhor tem sido bem atendido, é importante que o senhor saiba essas informações.<br/>
<br/>
Fechamos <font class="atencao">0</font> contrato(s) no valor total de <font class="atencao">R$ 0,00</font>, onde o senhor receberá valor líquido de <font class="atencao">R$ 0,00</font> (ou aproximadamente <b>R$ 0,00</b> / ou ainda, o valor líquido confirmaremos mediante os boletos que estamos aguardando chegar do Banco <font class="atencao">_____</font>) 
<br/>
No total, o senhor pagará por mês <font class="atencao">R$ 0,00</font> que se refere às parcelas <font class="atencao">R$0,00</font>,<font class="atencao">R$0,00</font>, <font class="atencao">R$0,00</font>, <font class="atencao">R$0,00</font>, <font class="atencao">R$0,00</font> e <font class="atencao">R$0,00</font> que serão descontadas do seu benefício em <font class="atencao">00X</font>, com primeiro vencimento para <font class="atencao">________</font>.<br/>


</div>


<div id="reembolsoParcela" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Reembolso Parcela</h2><br/>
<b>O discurso ao cliente deve ser simples:</b><br/>
Senhor (a), pode acontecer do Banco incluir a parcela seguinte no boleto de quitação e ainda assim gerar desconto, se isso acontecer, haverá o reembolso dessa parcela.<br/> 
Caso contrário não. Se houver o reembolso, consigo liberar mais tanto ao senhor (a).<br/>
<br/>
<b>OBS:</b> Nunca afirmar que haverá o reembolso, porém com o boleto em mãos e sabendo o dia do fechamento da folha do cliente, é possível ter certeza.<br/>


</div>

<%@ include file="/footer.jspf" %> 