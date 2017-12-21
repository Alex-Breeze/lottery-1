/**
 * Runs a lottery game in the frontend
 */

function updatePot() {
	$.getJSON('/api/pot-info', {}, (pot) => {
		$('#jackpot').html('' + pot.jackpot);
		$('#pricePerGame').html('' + pot.pricePerGame);
	});
}

function playGame() {
	$.getJSON('/api/play-game', {}, (game) => {
		$('#game-result-outcome').html(game.result.lotteryResultStatus);
		if(game.result.lotteryResultStatus != 'Blank') {
			$('#game-result-sum-block').show();
			$('#game-result-sum-span').html(game.ticket.prize);
		}else{
			$('#game-result-sum-block').hide();
		}
		$('#game-result-row1').html(game.row1);
		$('#game-result-row2').html(game.row2);
		$('#game-result-row3').html(game.row3);
		$('#game-result-block').show();
		updatePot();
	});
}

$(document).ready(() => {
	$('#game-result-block').hide();
	updatePot();
	$('#playGameButton').click(() => {
		playGame();
	});
});