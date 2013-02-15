<%@ include file="/header.jspf" %>

	<ul class="nav nav-tabs" id="myTab">
	  <li class="active"><a href="#home">Home</a></li>
	  <li><a href="#profile">Profile</a></li>
	  <li><a href="#messages">Messages</a></li>
	  <li><a href="#settings">Settings</a></li>
	</ul>

	<div class="tab-content">
	  <div class="tab-pane active" id="home">TESTE1</div>
	  <div class="tab-pane" id="profile">TESTE2</div>
	  <div class="tab-pane" id="messages">TESTE3</div>
	  <div class="tab-pane" id="settings">TESTE4</div>
	</div>

	<script>
  	$(function () {
	    $('#myTab a:last').tab('show');
  	});
	</script>
<%@ include file="/footer.jspf" %> 