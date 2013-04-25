<%@ include file="/header.jspf" %> 

<table id="mytable" style="width: 710px">
		<tr>
			<th scope="col" class="label_txt" style="width: 150px">
				Data de Atualização
			</th>
			<th scope="col" class="label_txt" style="width: 200px">
				Banco
			</th>
			<th scope="col" class="label_txt" style="width: 150px">
				Produto
			</th>
			<th scope="col" class="label_txt" style="width: 200px">
				Tabela
			</th>
			<th scope="col" class="label_txt" style="width: 80px">
				Valor
			</th>
			<th scope="col" class="label_txt" style="width: 80px">
				Meta
			</th>
		</tr>	
		<c:forEach items="${coeficientes}" var="coeficiente">
		
		<tr>
			<td class="label_txt"><fmt:formatDate value="${coeficiente.inclusao.time}" pattern="dd/MM/yyyy" /></td>
			<td class="label_txt">${coeficiente.tabela.produto.banco.nome}</td>
			<td class="label_txt">${coeficiente.tabela.produto.nome}</td>
			<td class="label_txt">${coeficiente.tabela.nome}</td>
			<td class="label_txt"><fmt:formatNumber value="${coeficiente.valor}" pattern="0.00000" /></td>
			<td class="label_txt"><fmt:formatNumber value="${coeficiente.percentualMeta}" pattern="0%" /></td>
		</tr>
	</c:forEach>
</table>

<input type="button" value="Voltar" onClick="history.go(-1)" class="form_button">

<%@ include file="/footer.jspf" %> 