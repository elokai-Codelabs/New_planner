$(document).ready(function () { 
	
	function displayRoute(station1, station2)
	{
		$('#container').empty();
				
		$.getJSON('GetRoute', {station1:station1,station2:station2}, function(route) {
			
			t = 1; //route id's
			
			var myRoute = '<div class="panel-group" id="accordion">';
			
			$.each(route, function() {
				
				if (t == 1) {
					myRoute += '<div class="panel panel-default"><div class="panel-heading">';
					myRoute += '<h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse'+t.toString()+'">';
					myRoute += 'Recommended Route <p class="alignright">Transfers: '+this.count+'</p>';
					myRoute += '</a></h4></div><div id="collapse'+t.toString()+'" class="panel-collapse collapse in"><div class="panel-body">';
				} else {
					myRoute += '<div class="panel panel-default"><div class="panel-heading">';
					myRoute += '<h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse'+t.toString()+'">';
					myRoute += 'Alternate Route '+(t-1)+'<p class="alignright">Transfers: '+this.count+'</p>';
					myRoute += '</a></h4></div><div id="collapse'+t.toString()+'" class="panel-collapse collapse"><div class="panel-body">';
				}
				
				myRoute += '<table id="myRoute'+t.toString()+'" class="table table-striped table-bordered table-condensed tablebottom">';
				myRoute +=  '<thead><tr><th class="routeColumn"> Start <img class=\"tubeIcon\" src=\"img\/icon.png\"> </th><th>Station</th><th>Line</th></tr></thead><tbody>';	

				x = 1; //counter for column
				transfers = 0; //transfer counter
				var prevline = '';
				var directions = '';
				
				stops = this.pairedStations.length;
				
				$.each(this.pairedStations, function() {
					
					var line = '';
					
					if (x == 1) {
						directions += '<p>At '+this.name+' take the ';
					}
					
					$.each(this.lines, function() {
						line = this;
						
						if (x==1) {
							directions += line+' line.</p>';
						}
					});
					
					if (String(line) != String(prevline) && String(prevline) != '') {
						directions += '<p>Change at '+this.name+' to the '+line+' line.</p>';
					} if (x == stops) {
						directions += 'Leave the train at '+this.name+'.';
					}
					
					myRoute += "<tr><td>"+x+"</td><td>"+this.name+"</td><td>"+line+"</td></tr>";
					x++;
					
					prevline = line;					
			    });
				
				myRoute +=  "</tbody></table>";
				
				myRoute +='<button class="btn btn-warning btn-xs alignright" data-toggle="modal" data-target="#myModal'+t.toString()+'">Directions</button>';
				
				myRoute +='<div class="modal fade" id="myModal'+t.toString()+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
				myRoute +='<div class="modal-dialog modelposition"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" id="myModalLabel">Directions</h4></div>';
				myRoute +=' <div class="modal-body">'+directions+'</div></div></div></div>';
				
				myRoute +=  "</div></div></div>";
				t++;
			});
			myRoute += '</div>';
			$("#container").append(myRoute);
		});
	}
	$('#station1').change(function() {
		$('.selectpicker').selectpicker('refresh');
		displayRoute($("#station1 option:selected").text(), $("#station2 option:selected").text());
	});
	$('#station2').change(function() {
		$('.selectpicker').selectpicker('refresh');
		displayRoute($("#station1 option:selected").text(), $("#station2 option:selected").text());
	});
	$('#swap').click(function(){
		var v1 = $('#station1').val();
		var v2 = $('#station2').val();
		$('#station1').val(v2);
		$('#station2').val(v1);
		
		displayRoute($("#station1 option:selected").text(), $("#station2 option:selected").text());
		$('.selectpicker').selectpicker('refresh');
	});
	$('.selectpicker').selectpicker('show');
});