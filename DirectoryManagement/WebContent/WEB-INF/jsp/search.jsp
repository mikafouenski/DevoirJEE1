<nav>
	<form method="POST" action="${pageContext.request.contextPath}/searchGroup">
			<table>
				<tr>Recherche Groupe</tr>
				<tr>
					<td>Nom <input type="text" name="name" class="form-control" /></td>
					<td><button type="submit" class="btn btn-info">Valider</button></td>
				</tr>
			</table>
		</form>
		<form method="POST" action="${pageContext.request.contextPath}/searchPerson">
			<table>
				<tr>Recherche Personne</tr>
				<tr>
					<td>Nom <input type="text" name="name" class="form-control" /></td>
					<td>Prenom <input type="text" name="firstname" class="form-control" /></td>
					<td><button type="submit" class="btn btn-info">Valider</button></td>
				</tr>
			</table>
		</form>
		
</nav>