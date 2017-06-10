public class ChessBox implements java.io.Serializable {
	int distance;//常数
	Point[] boxPoint;//point型棋盒数组

	ChessBox(int distance, int k1, int k2) {
		this.distance = distance;
		boxPoint = new Point[16];//数组有16个元素，即16个位置放棋子
		int Hspace = distance, Vspace = distance;
		for (int i = 0; i < 4; i++) {//第一行循环分配位置
			boxPoint[i] = new Point(k1 * distance + Hspace, k2 * Vspace);
			boxPoint[i].setIsBoxPoint(true);
			Hspace = Hspace + distance;
		}
		Hspace = distance;
		Vspace = distance;
		for (int i = 4; i < 8; i++) {//第2行循环分配位置
			boxPoint[i] = new Point(k1 * distance + Hspace, (k2 + 1) * Vspace);
			boxPoint[i].setIsBoxPoint(true);
			Hspace = Hspace + distance;
		}
		Hspace = distance;
		Vspace = distance;
		for (int i = 8; i < 12; i++) {//第3行循环分配位置
			boxPoint[i] = new Point(k1 * distance + Hspace, (k2 + 2) * Vspace);
			boxPoint[i].setIsBoxPoint(true);
			Hspace = Hspace + distance;
		}
		Hspace = distance;
		Vspace = distance;
		for (int i = 12; i < 16; i++) {//第4行循环分配位置
			boxPoint[i] = new Point(k1 * distance + Hspace, (k2 + 3) * Vspace);
			boxPoint[i].setIsBoxPoint(true);
			Hspace = Hspace + distance;
		}
	}

	public Point[] getBoxPoint() {
		return boxPoint;
	}

	public Point putPieceToBox(ChessPiece piece) {//棋子按每个棋子特定位置回到棋盒中
		int w = piece.getBounds().width;//利于下面对棋子位置的定位
		int h = piece.getBounds().height;
		Point p = piece.getAtPoint();
		boolean boo = false;
		if (p != null)//棋子位置不为空
			boo = p.getIsBoxPoint();//boo是棋子是否在棋盒中的判断
		String name = piece.getName();
		if (boo == false) {//如果该棋子不在棋盒中，即可以放入棋盒
			if (name.equals("帅") || name.equals("将")) {//不同棋子不同位置
				piece.setLocation(boxPoint[0].getX() - w / 2, boxPoint[0].getY() - h / 2);
				boxPoint[0].setChessPiece(piece);
				boxPoint[0].setHaveChessPiece(true);
				piece.setAtPoint(boxPoint[0]);
			}
			if (name.equals("士") || name.equals("仕")) {
				if (boxPoint[1].isHaveChessPiece() == false) {//因为“士”或“仕”都有俩个，优先向前排列
					piece.setLocation(boxPoint[1].getX() - w / 2, boxPoint[1].getY() - h / 2);
					boxPoint[1].setChessPiece(piece);
					boxPoint[1].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[1]);
				} else {
					piece.setLocation(boxPoint[2].getX() - w / 2, boxPoint[2].getY() - h / 2);
					boxPoint[2].setChessPiece(piece);
					boxPoint[2].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[2]);
				}
			}
			if (name.equals("象") || name.equals("相")) {
				if (boxPoint[3].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[3].getX() - w / 2, boxPoint[3].getY() - h / 2);
					boxPoint[3].setChessPiece(piece);
					boxPoint[3].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[3]);
				} else {
					piece.setLocation(boxPoint[4].getX() - w / 2, boxPoint[4].getY() - h / 2);
					boxPoint[4].setChessPiece(piece);
					boxPoint[4].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[4]);
				}
			}
			if (name.equals("马") || name.equals("馬")) {
				if (boxPoint[5].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[5].getX() - w / 2, boxPoint[5].getY() - h / 2);
					boxPoint[5].setChessPiece(piece);
					boxPoint[5].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[5]);
				} else {
					piece.setLocation(boxPoint[6].getX() - w / 2, boxPoint[6].getY() - h / 2);
					boxPoint[6].setChessPiece(piece);
					boxPoint[6].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[6]);
				}
			}
			if (name.equals("车") || name.equals("車")) {
				if (boxPoint[7].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[7].getX() - w / 2, boxPoint[7].getY() - h / 2);
					boxPoint[7].setChessPiece(piece);
					boxPoint[7].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[7]);
				} else {
					piece.setLocation(boxPoint[8].getX() - w / 2, boxPoint[8].getY() - h / 2);
					boxPoint[8].setChessPiece(piece);
					boxPoint[8].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[8]);
				}
			}
			if (name.equals("炮")) {
				if (boxPoint[9].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[9].getX() - w / 2, boxPoint[9].getY() - h / 2);
					boxPoint[9].setChessPiece(piece);
					boxPoint[9].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[9]);
				} else {
					piece.setLocation(boxPoint[10].getX() - w / 2, boxPoint[10].getY() - h / 2);
					boxPoint[10].setChessPiece(piece);
					boxPoint[10].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[10]);
				}
			}
			if (name.equals("兵") || name.equals("卒")) {
				if (boxPoint[11].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[11].getX() - w / 2, boxPoint[11].getY() - h / 2);
					boxPoint[11].setChessPiece(piece);
					boxPoint[11].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[11]);
				} else if (boxPoint[12].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[12].getX() - w / 2, boxPoint[12].getY() - h / 2);
					boxPoint[12].setChessPiece(piece);
					boxPoint[12].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[12]);
				} else if (boxPoint[13].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[13].getX() - w / 2, boxPoint[13].getY() - h / 2);
					boxPoint[13].setChessPiece(piece);
					boxPoint[13].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[13]);
				} else if (boxPoint[14].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[14].getX() - w / 2, boxPoint[14].getY() - h / 2);
					boxPoint[14].setChessPiece(piece);
					boxPoint[14].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[14]);
				} else if (boxPoint[15].isHaveChessPiece() == false) {
					piece.setLocation(boxPoint[15].getX() - w / 2, boxPoint[15].getY() - h / 2);
					boxPoint[15].setChessPiece(piece);
					boxPoint[15].setHaveChessPiece(true);
					piece.setAtPoint(boxPoint[15]);
				}
			}
		}
		Point returnPoint = piece.getAtPoint();
		return returnPoint;
	}
}