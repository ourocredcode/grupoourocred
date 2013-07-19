<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<HTML>
	<TITLE>OFFLINE Detalhamento de crédito</TITLE>

	<HEAD></HEAD>

<BODY background=http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/fnada.gif aLink=#0000ff link=#004080 topMargin=0 bgColor=#ffffff text=#000000 vLink=#ff0000 marginwidth="0" marginheight="0">

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" height=70 name="topo">

<TBODY>
<TR bgColor=#065ca5>
<TD vAlign=top align=left>
<TABLE border=0 cellSpacing=0 cellPadding=0>

<TBODY>
<TR>

<TD vAlign=top align=left><A href="http://www.mpas.gov.br/"><IMG border=0 src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/KO.gif" width=94 height=70></A></TD>

<TD vAlign=top align=left><BR>
	<A href="http://www.mpas.gov.br/">
		<IMG border=0 src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/previdencia.gif" width=290 height=41>
	</A>
</TD>
</TR>
</TBODY>

</TABLE>
</TD>
<TD vAlign=top align=right><A href="http://www.redegoverno.gov.br/"><IMG border=0 hspace=0
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/prevnet.gif" width=139
height=70></A></TD></TR></TBODY></TABLE>
<CENTER><FONT color=#065ca5 face="Trebuchet MS">
<H2 align=center>Detalhamento de Crédito</H2>
<TABLE border=1 width="69%" height=1>
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=1 vAlign=top width="30%" align=middle>
<P align=left>
	<SMALL><SMALL><FONT face="Trebuchet MS">Número do Benefício</FONT></SMALL></SMALL>
</P></TD>
<TD bgColor=#c0c0c0 height=1 vAlign=top width="70%" align=middle>
<P align=left><SMALL><SMALL><FONT face="Trebuchet MS">Nome do
Segurado</FONT></SMALL></SMALL></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD height=24 width="30%" align=middle>
<P align=center><STRONG><FONT face="Trebuchet MS"><f:formatString pattern="###.###.###-#">${detalhamento.matricula }</f:formatString></FONT></STRONG></P></TD>
<TD height=24 width="70%" align=middle>
<P align=left><STRONG><FONT face="Trebuchet MS">${detalhamento.nome }</FONT></STRONG></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%" height=1>
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=1 vAlign=top width="16%"><SMALL><SMALL><FONT face="Trebuchet
MS">Competência</FONT></SMALL></SMALL></TD>
<TD bgColor=#c0c0c0 height=1 vAlign=top width="42%"><SMALL><SMALL><FONT face="Trebuchet MS">Período a que se refere o crédito
:</FONT></SMALL></SMALL></TD>
<TD bgColor=#c0c0c0 height=1 vAlign=top width="38%"><SMALL><SMALL><FONT face="Trebuchet MS">Pagamento através de
:</FONT></SMALL></SMALL></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD height=22 width="16%">
<P align=center><STRONG><FONT face="Trebuchet MS">${detalhamento.competencia }</FONT></STRONG></P></TD>
<TD height=22 width="18%">
<P align=center><STRONG><FONT face="Trebuchet MS"><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${detalhamento.periodoinicial }" /></FONT></STRONG></P></TD>
<TD height=22 width="5%">
<P align=center><FONT face="Trebuchet MS">a</FONT></P></TD>
<TD height=22 width="20%">
<P align=center><STRONG><FONT face="Trebuchet MS"><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${detalhamento.periodofinal }" /></FONT></STRONG></P></TD>
<TD height=22 vAlign=top width="42%">
<P align=left><STRONG><FONT face="Trebuchet MS">${detalhamento.meio_pgtodesc }</FONT></STRONG></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%" height=23>
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=6 vAlign=top width="100%" align=left checked="false"><SMALL><FONT face="Trebuchet
MS"><SMALL>Espécie</SMALL></FONT></SMALL></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD height=9 width="10%">
<P align=left><STRONG><FONT face="Trebuchet MS">${detalhamento.especie }</FONT></STRONG></P></TD>
<TD height=9 width="70%"><STRONG><FONT face="Trebuchet MS">${detalhamento.descespecie }</FONT></STRONG></TD>
<TD height=9 width="20%">
<P align=center><STRONG><FONT face="Trebuchet MS"></FONT></STRONG></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%" height=14>
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=2 vAlign=top width="21%">
<P align=left><SMALL><SMALL><FONT face="Trebuchet MS">Banco</FONT></SMALL></SMALL></P></TD>
<TD bgColor=#c0c0c0 height=2 vAlign=top width="54%"><SMALL><SMALL><FONT face="Trebuchet MS">Agência
bancária</FONT></SMALL></SMALL></TD>
<TD bgColor=#c0c0c0 height=2 vAlign=top width="25%">
<P align=center><SMALL><SMALL><FONT face="Trebuchet MS">Código do
Banco</FONT></SMALL></SMALL></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD height=9 width="21%">
<P align=left><STRONG><FONT face="Trebuchet MS">${detalhamento.banco }</FONT></STRONG></P></TD>
<TD height=9 width="54%"><STRONG><FONT face="Trebuchet MS">${detalhamento.agencia }</FONT></STRONG></TD>
<TD height=9 width="25%">
<P align=center><STRONG><FONT face="Trebuchet
MS">${detalhamento.codbanco }</FONT></STRONG></P></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%" height=23>
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=6 vAlign=top width="64%" align=left checked="false"><SMALL><FONT face="Trebuchet
MS"><SMALL>Endereço do banco</SMALL></FONT></SMALL></TD>
<TD bgColor=#c0c0c0 height=6 vAlign=top width="36%" align=left checked="false"><SMALL><FONT face="Trebuchet
MS"><SMALL>Disponível para recebimento de :</SMALL></FONT></SMALL></TD></TR></TBODY></TABLE>
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD height=5 width="65%"><SMALL><STRONG><FONT face="Trebuchet
MS">${detalhamento.enderecobanco }</FONT></STRONG></SMALL></TD>
<TD style="BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; BORDER-TOP: 1px solid; BORDER-RIGHT: 1px solid" height=5
width="15%">
<P align=center><STRONG><FONT face="Trebuchet MS"><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${detalhamento.dispinicial }" /></FONT></STRONG></P></TD>
<TD style="BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; BORDER-TOP: 1px solid; BORDER-RIGHT: 1px solid" height=5
width="5%"><FONT face="Trebuchet MS">
<P align=center>a</FONT></P></TD>
<TD style="BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; BORDER-TOP: 1px solid; BORDER-RIGHT: 1px solid" height=5
width="17%"><FONT face="Trebuchet MS">
<P align=center><STRONG><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${detalhamento.dispfinal }" /></STRONG></FONT></P></TD></TR></TBODY></TABLE><SMALL><FONT
face="Trebuchet MS">
<TABLE border=1 width="69%">
<TBODY>
<TR>
<TD bgColor=#c0c0c0 height=18 colSpan=2>
<P align=center><SMALL><STRONG><FONT face="Trebuchet MS">C R É D I T O S </FONT></STRONG></SMALL></P></TD></TR>
<TR>
<TD height=18 width=366>
<P align=center><FONT face="Trebuchet MS"><STRONG>Descrição das Rubricas</STRONG></FONT></P></TD>
<TD height=18 width=145>
<P align=center><FONT face="Trebuchet MS"><STRONG>Valor</FONT></STRONG></P></TD></TR>

	<c:forEach items="${detalhamento.creditos }" var="credito">
		<TR>
			<TD>
				<c:set var="keypart" value="${fn:split(credito.key, ':')}" />
				<c:out value="${keypart[1] }" />
			</TD>
			<TD style=" text-align: right;"><fmt:formatNumber type="NUMBER" value="${credito.value }" minFractionDigits="2" /></TD>
		<TR>
	</c:forEach>

<TR>
<TD bgColor=#c0c0c0 height=18 colSpan=2>
<P align=center><STRONG><FONT face="Trebuchet MS"><SMALL>D É B I T O S</SMALL></STRONG></FONT></P></TD></TR>

	<c:forEach items="${detalhamento.debitos }" var="debito" >
		<TR>
			<TD>
				<c:set var="keypart" value="${fn:split(debito.key, ':')}" />
				<c:out value="${keypart[1] }" />
			</TD>
			<TD style=" text-align: right;"><fmt:formatNumber type="NUMBER" value="${debito.value }" minFractionDigits="2" /></TD>
		</TR>
	</c:forEach>

</TBODY></TABLE></FONT></SMALL>

<TABLE border=1 cellPadding=2 width="69%">
<TBODY>
<TR>
<TD style="FONT-FAMILY: Trebuchet MS" bgColor=#c0c0c0 width="33%" align=middle><STRONG>Valor Bruto </STRONG></TD>
<TD style="FONT-FAMILY: Trebuchet MS" bgColor=#c0c0c0 width="33%" align=middle><STRONG>Valor&nbsp; dos
Descontos</STRONG></TD>
<TD style="FONT-FAMILY: Trebuchet MS" bgColor=#c0c0c0 width="34%" align=middle><STRONG>Valor Líquido</STRONG></TD></TR>
<TR>
<TD width="33%" align=right><STRONG><FONT face="Trebuchet MS"><fmt:formatNumber type="NUMBER" value="${detalhamento.valorBruto }" minFractionDigits="2" /></FONT></STRONG></TD>
<TD width="33%" align=right><STRONG><FONT face="Trebuchet MS"><fmt:formatNumber type="NUMBER" value="${detalhamento.valorDescontos }" minFractionDigits="2" /></FONT></STRONG></TD>
<TD width="34%" align=right><STRONG><FONT face="Trebuchet
MS"><fmt:formatNumber type="NUMBER" value="${detalhamento.valorLiquido }" minFractionDigits="2" /></FONT></STRONG></TD></TR></TBODY></TABLE>
<TABLE border=0 width="69%">
<TBODY>
<TR>
<TD width="100%">
<P align=center><U><STRONG><FONT color=#065ca5 face="Trebuchet MS">Este extrato vale para simples
conferência</FONT></STRONG></U></P></TD></TR></TBODY></TABLE><A href="JavaScript:location.href='../contexto/' +
parent.link"><IMG border=0 src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/ante.gif" width=120
height=38></A></CENTER>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#065ca5 height=35>
<TBODY>
<TR>
<TD vAlign=top width="1%" align=left><IMG border=0
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/esquerda3b.gif" width=70 height=62></TD>
<TD width="60%" align=left><IMG border=0 src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/rodape_dtp.gif"
width=93 height=34><BR><IMG alt="bluebottom2.gif (971 bytes)"
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/bluebottom2.gif" width=42 height=10><IMG border=0
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/dtpextenso2d.gif"><BR></TD>
<TD width="40%" align=right><A title="Fale com a DATAPREV" href="mailto:webmaster.dtp@rjo.dataprev.gov.br"><IMG border=0
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/mailslot.gif" width=36 height=25></A></TD>
<TD vAlign=top width="1%" align=right><IMG border=0
src="http://www3.dataprev.gov.br/cws/CONTEXTO/HISCRE/IMAGENS/padrao1girado.gif" width=70
height=62></TD></TR></TBODY></TABLE></FONT></BODY></HTML>

