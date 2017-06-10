public class MoveRule implements java.io.Serializable {
	ChessPiece piece = null;//piece用来存放被拖动的棋子的引用
	Point[][] point;//用来存放ChessPoint类中的PlayPoint
	Point startPoint, endPoint;//用来存放棋子的起点和终点

	public void setPoint(Point[][] point) {//可以将ChessPoint类中的PlayPoint引用传递给point
		this.point = point;
	}

	public boolean movePieceRule(ChessPiece piece, Point startPoint, Point endPoint) {
		//用来判断棋子从起点startPoint走到终点endPoint是否遵守了象棋规则
		int startI = -1, startJ = -1, endI = -1, endJ = -1;
		this.piece = piece;//将ChessPiece中的piece存放的被拖动的棋子的引用传递给piece
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		boolean isMove = false;
		for (int i = 0; i < point.length; i++) {//横向
			for (int j = 0; j < point[i].length; j++) {//直向
				if (startPoint.equals(point[i][j])) {
					startI = i;
					startJ = j;
					break;
				}
			}
		}
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				if (endPoint.equals(point[i][j])) {
					endI = i;
					endJ = j;
					break;
				}
			}
		}
		int minI = Math.min(startI, endI);
		int maxI = Math.max(startI, endI);
		int minJ = Math.min(startJ, endJ);
		int maxJ = Math.max(startJ, endJ);
		
		//车每一步可以直进、直退、横走，不限步数，可以过河
		if (piece.getName().equals("车") || piece.getName().equals("車")) {
			if (startI == endI) {//直向不变，即横行时
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isHaveChessPiece()) {//行进过程中有棋子
						isMove = false;
						break;
					}
				}
				if (j == maxJ)
					isMove = true;
			} else if (startJ == endJ) { //横向相等，即纵行时
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isHaveChessPiece()) {
						isMove = false;
						break;
					}
				}
				if (i == maxI)
					isMove = true;
			} else
				isMove = false;
			
		} 
		//马走日字，若有棋子则不能走。
		else if (piece.getName().equals("马") || piece.getName().equals("馬")) {
			int xAxle = Math.abs(startI - endI);//直向差值的绝对值
	           int yAxle=Math.abs(startJ-endJ);//横向差值的绝对值
	           if(xAxle==2&&yAxle==1){//直2横1
	             if(endI>startI){//前进
	                if(point[startI+1][startJ].isHaveChessPiece())//中间有棋子，则不行
						isMove = false;
					else
						isMove = true;
				}
				if (endI < startI) {//后退
					if (point[startI - 1][startJ].isHaveChessPiece())
						isMove = false;
					else
						isMove = true;
				}
			} else if (xAxle == 1 && yAxle == 2) {
				if (endJ > startJ) {
					if (point[startI][startJ + 1].isHaveChessPiece())
						isMove = false;
					else
						isMove = true;
				}
				if (endJ < startJ) {
					if (point[startI][startJ - 1].isHaveChessPiece())
						isMove = false;
					else
						isMove = true;
				}
			} else
				isMove = false;
		} 
		  //象不能过河界，走田字，若有棋子则不能走
		else if (piece.getName().equals("象")) {
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endI <= 4) {//走田字且不能过河  
				if (point[centerI][centerJ].isHaveChessPiece())//田字中间有棋子
					isMove = false;
				else
					isMove = true;
			} else
				isMove = false;
		} else if (piece.getName().equals("相")) {
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endI >= 5) {
				if (point[centerI][centerJ].isHaveChessPiece())
					isMove = false;
				else
					isMove = true;
			} else
				isMove = false;
		} 
		 //炮可以直进直退横走，不限步数。炮吃子时必须隔一个
		else if (piece.getName().equals("炮")) {
			int number = 0;
			if (startI == endI) {//直向相等，即横行时
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isHaveChessPiece())
						number++;
				}
				if (number > 1)
					isMove = false;
				else if (number == 1) {
					if (point[endI][endJ].isHaveChessPiece())
						isMove = true;
				} else if (number == 0 && !point[endI][endJ].isHaveChessPiece())
					isMove = true;
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isHaveChessPiece())
						number++;
				}
				if (number > 1)
					isMove = false;
				else if (number == 1) {
					if (point[endI][endJ].isHaveChessPiece())
						isMove = true;
				} else if (number == 0 && !point[endI][endJ].isHaveChessPiece())
					isMove = true;
			} else
				isMove = false;
		} 
		//兵在没有过河时，只能向前直走一步，过河后，可向前直走或横走一步，不能后退
		else if (piece.getName().equals("兵")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI >= 5) {//不过河，可以向前走一步，不能横走
				if (startI - endI == 1 && yAxle == 0)
					isMove = true;
				else
					isMove = false;
			} else if (endI <= 4) {//过河，可向前可横走
				if ((startI - endI == 1) && (yAxle == 0))
					isMove = true;
				else if ((endI - startI == 0) && (yAxle == 1))
					isMove = true;
				else
					isMove = false;
			}
		} else if (piece.getName().equals("卒")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 4) {
				if (endI - startI == 1 && yAxle == 0)
					isMove = true;
				else
					isMove = false;
			} else if (endI >= 5) {
				if ((endI - startI == 1) && (yAxle == 0))
					isMove = true;
				else if ((endI - startI == 0) && (yAxle == 1))
					isMove = true;
				else
					isMove = false;
			}
		} 
		 //士每步只能沿九宫斜走，可进可退。
		else if (piece.getName().equals("士") || piece.getName().equals("仕")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			boolean c1 = (endJ >= 3 && endJ <= 5);
			boolean c2 = (endI >= 7 && endI <= 9);
			boolean d1 = (endJ >= 3 && endJ <= 5);
			boolean d2 = (endI >= 0 && endI <= 2);
			boolean ok = (xAxle == 1 && yAxle == 1);
			if ((ok && c1 && c2) || (ok && d1 && d2))
				isMove = true;
			else
				isMove = false;
			}
			/*
			 * MoveRule 类中的一小段，原有代码无法执行飞将操作，即当将和帅面对面且中间	无其他棋子时，将（帅）可以吃掉帅（将），
			 * 修改后可以执行飞将操作
			 */
			
			//帅将每次只能走一步，但不能走出九宫，不能在同一直线上，必须回避。
		 else if ((piece.getName().equals("帅")) || (piece.getName().equals("将"))) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);

			boolean c1 = (endJ >= 3 && endJ <= 5);
			boolean c2 = (endI >= 7 && endI <= 9);
			boolean d1 = (endJ >= 3 && endJ <= 5);
			boolean d2 = (endI >= 0 && endI <= 2);
			boolean ok = (xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1);
			if ((ok && c1 && c2) || (ok && d1 && d2))
				isMove = true;
			else if (maxI - minI > 4 && endJ == startJ) {
				int i = 0;
				for (i = minI + 1; i < maxI; i++) {
					if (point[i][startJ].isHaveChessPiece()) {
						isMove = false;
						break;
					}
				}
				if (i == maxI && point[endI][endJ].haveChessPiece) {
					ok = point[endI][endJ].getChessPiece().getName().equals("帅")
							|| point[endI][endJ].getChessPiece().getName().equals("将");
					if (ok) {
						isMove = true;
					}
				}
			} else
				isMove = false;
		}
		if (startPoint.equals(endPoint))
			isMove = false;
		return isMove;
	}
}
