import java.util.ArrayList;

public class InitPieceLocation implements java.io.Serializable {
	Point[][] playPoint;
	ChessPiece[] redPiece, blackPiece;
	ChessBox redChessBox, blackChessBox;

	public void setPoint(Point[][] p) {
		playPoint = p;
	}

	public void setRedChessPiece(ChessPiece[] redPiece) {
		this.redPiece = redPiece;
	}

	public void setBlackChessPiece(ChessPiece[] blackPiece) {
		this.blackPiece = blackPiece;
	}

	public void setRedChessBox(ChessBox redChessBox) {
		this.redChessBox = redChessBox;
	}

	public void setBlackChessBox(ChessBox blackChessBox) {
		this.blackChessBox = blackChessBox;
	}

	public void putAllPieceToPlayChessArea() {
		for (int i = 0; i < playPoint.length; i++) {
			for (int j = 0; j < playPoint[i].length; j++) {
				playPoint[i][j].setHaveChessPiece(false);
			}
		}
		ArrayList<ChessPiece> pieceList = new ArrayList<ChessPiece>();
		for (int i = 0; i < redPiece.length; i++) {
			pieceList.add(redPiece[i]);
			pieceList.add(blackPiece[i]);
		}
		for (int i = 0; i < blackPiece.length; i++) {
			pieceList.add(blackPiece[i]);
		}
		for (int k = 0; k < pieceList.size(); k++) {
			ChessPiece piece = pieceList.get(k);
			int w = piece.getBounds().width;
			int h = piece.getBounds().height;
			Point p = null;
			p = piece.getAtPoint();
			if (p != null)
				p.setHaveChessPiece(false);
			String name = piece.getName();
			if (name.equals("帅") && piece.getIsRed()) {
				piece.setLocation(playPoint[9][4].getX() - w / 2, playPoint[9][4].getY() - h / 2);
				piece.setAtPoint(playPoint[9][4]);
				playPoint[9][4].setHaveChessPiece(true);
				playPoint[9][4].setChessPiece(piece);
			}
			if ((name.equals("士") || name.equals("仕")) && piece.getIsRed()) {
				if (playPoint[9][3].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[9][3].getX() - w / 2, playPoint[9][3].getY() - h / 2);
					piece.setAtPoint(playPoint[9][3]);
					playPoint[9][3].setHaveChessPiece(true);
					playPoint[9][3].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[9][5].getX() - w / 2, playPoint[9][5].getY() - h / 2);
					piece.setAtPoint(playPoint[9][5]);
					playPoint[9][5].setHaveChessPiece(true);
					playPoint[9][5].setChessPiece(piece);
				}
			}
			if (name.equals("相") && piece.getIsRed()) {
				if (playPoint[9][2].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[9][2].getX() - w / 2, playPoint[9][2].getY() - h / 2);
					piece.setAtPoint(playPoint[9][2]);
					playPoint[9][2].setHaveChessPiece(true);
					playPoint[9][2].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[9][6].getX() - w / 2, playPoint[9][6].getY() - h / 2);
					piece.setAtPoint(playPoint[9][6]);
					playPoint[9][6].setHaveChessPiece(true);
					playPoint[9][6].setChessPiece(piece);
				}
			}
			if ((name.equals("马") || name.equals("馬")) && piece.getIsRed()) {
				if (playPoint[9][1].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[9][1].getX() - w / 2, playPoint[9][1].getY() - h / 2);
					piece.setAtPoint(playPoint[9][1]);
					playPoint[9][1].setHaveChessPiece(true);
					playPoint[9][1].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[9][7].getX() - w / 2, playPoint[9][7].getY() - h / 2);
					piece.setAtPoint(playPoint[9][7]);
					playPoint[9][7].setHaveChessPiece(true);
					playPoint[9][7].setChessPiece(piece);
				}
			}
			if ((name.equals("车") || name.equals("車")) && piece.getIsRed()) {
				if (playPoint[9][0].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[9][0].getX() - w / 2, playPoint[9][0].getY() - h / 2);
					piece.setAtPoint(playPoint[9][0]);
					playPoint[9][0].setHaveChessPiece(true);
					playPoint[9][0].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[9][8].getX() - w / 2, playPoint[9][7].getY() - h / 2);
					piece.setAtPoint(playPoint[9][8]);
					playPoint[9][8].setHaveChessPiece(true);
					playPoint[9][8].setChessPiece(piece);
				}
			}
			if (name.equals("炮") && piece.getIsRed()) {
				if (playPoint[7][1].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[7][1].getX() - w / 2, playPoint[7][1].getY() - h / 2);
					piece.setAtPoint(playPoint[7][1]);
					playPoint[7][1].setHaveChessPiece(true);
					playPoint[7][1].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[7][7].getX() - w / 2, playPoint[7][7].getY() - h / 2);
					piece.setAtPoint(playPoint[7][7]);
					playPoint[7][7].setHaveChessPiece(true);
					playPoint[7][7].setChessPiece(piece);
				}
			}
			if (name.equals("兵") && piece.getIsRed()) {
				if (playPoint[6][0].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[6][0].getX() - w / 2, playPoint[6][0].getY() - h / 2);
					piece.setAtPoint(playPoint[6][0]);
					playPoint[6][0].setHaveChessPiece(true);
					playPoint[6][0].setChessPiece(piece);
				} else if (playPoint[6][2].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[6][2].getX() - w / 2, playPoint[6][2].getY() - h / 2);
					piece.setAtPoint(playPoint[6][2]);
					playPoint[6][2].setHaveChessPiece(true);
					playPoint[6][2].setChessPiece(piece);
				} else if (playPoint[6][4].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[6][4].getX() - w / 2, playPoint[6][4].getY() - h / 2);
					piece.setAtPoint(playPoint[6][4]);
					playPoint[6][4].setHaveChessPiece(true);
					playPoint[6][4].setChessPiece(piece);
				} else if (playPoint[6][6].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[6][6].getX() - w / 2, playPoint[6][6].getY() - h / 2);
					piece.setAtPoint(playPoint[6][6]);
					playPoint[6][6].setHaveChessPiece(true);
					playPoint[6][6].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[6][8].getX() - w / 2, playPoint[6][8].getY() - h / 2);
					piece.setAtPoint(playPoint[6][8]);
					playPoint[6][8].setHaveChessPiece(true);
					playPoint[6][8].setChessPiece(piece);
				}
			}
			if (name.equals("将") && piece.getIsBlack()) {
				piece.setLocation(playPoint[0][4].getX() - w / 2, playPoint[0][4].getY() - h / 2);
				piece.setAtPoint(playPoint[0][4]);
				playPoint[0][4].setHaveChessPiece(true);
				playPoint[0][4].setChessPiece(piece);
			}
			if ((name.equals("士") || name.equals("仕")) && piece.getIsBlack()) {
				if (playPoint[0][3].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[0][3].getX() - w / 2, playPoint[0][3].getY() - h / 2);
					piece.setAtPoint(playPoint[0][3]);
					playPoint[0][3].setHaveChessPiece(true);
					playPoint[0][3].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[0][5].getX() - w / 2, playPoint[0][5].getY() - h / 2);
					piece.setAtPoint(playPoint[0][5]);
					playPoint[0][5].setHaveChessPiece(true);
					playPoint[0][5].setChessPiece(piece);
				}
			}
			if (name.equals("象") && piece.getIsBlack()) {
				if (playPoint[0][2].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[0][2].getX() - w / 2, playPoint[0][2].getY() - h / 2);
					piece.setAtPoint(playPoint[0][2]);
					playPoint[0][2].setHaveChessPiece(true);
					playPoint[0][2].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[0][6].getX() - w / 2, playPoint[0][6].getY() - h / 2);
					piece.setAtPoint(playPoint[0][6]);
					playPoint[0][6].setHaveChessPiece(true);
					playPoint[0][6].setChessPiece(piece);
				}
			}
			if ((name.equals("马") || name.equals("馬")) && piece.getIsBlack()) {
				if (playPoint[0][1].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[0][1].getX() - w / 2, playPoint[0][1].getY() - h / 2);
					piece.setAtPoint(playPoint[0][1]);
					playPoint[0][1].setHaveChessPiece(true);
					playPoint[0][1].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[0][7].getX() - w / 2, playPoint[0][7].getY() - h / 2);
					piece.setAtPoint(playPoint[0][7]);
					playPoint[0][7].setHaveChessPiece(true);
					playPoint[0][7].setChessPiece(piece);
				}
			}
			if ((name.equals("车") || name.equals("車")) && piece.getIsBlack()) {
				if (playPoint[0][0].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[0][0].getX() - w / 2, playPoint[0][0].getY() - h / 2);
					piece.setAtPoint(playPoint[0][0]);
					playPoint[0][0].setHaveChessPiece(true);
					playPoint[0][0].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[0][8].getX() - w / 2, playPoint[0][7].getY() - h / 2);
					piece.setAtPoint(playPoint[0][8]);
					playPoint[0][8].setHaveChessPiece(true);
					playPoint[0][8].setChessPiece(piece);
				}
			}
			if (name.equals("炮") && piece.getIsBlack()) {
				if (playPoint[2][1].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[2][1].getX() - w / 2, playPoint[2][1].getY() - h / 2);
					piece.setAtPoint(playPoint[2][1]);
					playPoint[2][1].setHaveChessPiece(true);
					playPoint[2][1].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[2][7].getX() - w / 2, playPoint[2][7].getY() - h / 2);
					piece.setAtPoint(playPoint[2][7]);
					playPoint[2][7].setHaveChessPiece(true);
					playPoint[2][7].setChessPiece(piece);
				}
			}
			if (name.equals("卒") && piece.getIsBlack()) {
				if (playPoint[3][0].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[3][0].getX() - w / 2, playPoint[3][0].getY() - h / 2);
					piece.setAtPoint(playPoint[3][0]);
					playPoint[3][0].setHaveChessPiece(true);
					playPoint[3][0].setChessPiece(piece);
				} else if (playPoint[3][2].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[3][2].getX() - w / 2, playPoint[3][2].getY() - h / 2);
					piece.setAtPoint(playPoint[3][2]);
					playPoint[3][2].setHaveChessPiece(true);
					playPoint[3][2].setChessPiece(piece);
				} else if (playPoint[3][4].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[3][4].getX() - w / 2, playPoint[3][4].getY() - h / 2);
					piece.setAtPoint(playPoint[3][4]);
					playPoint[3][4].setHaveChessPiece(true);
					playPoint[3][4].setChessPiece(piece);
				} else if (playPoint[3][6].isHaveChessPiece() == false) {
					piece.setLocation(playPoint[3][6].getX() - w / 2, playPoint[3][6].getY() - h / 2);
					piece.setAtPoint(playPoint[3][6]);
					playPoint[3][6].setHaveChessPiece(true);
					playPoint[3][6].setChessPiece(piece);
				} else {
					piece.setLocation(playPoint[3][8].getX() - w / 2, playPoint[3][8].getY() - h / 2);
					piece.setAtPoint(playPoint[3][8]);
					playPoint[3][8].setHaveChessPiece(true);
					playPoint[3][8].setChessPiece(piece);
				}
			}

		}
	}

	public void putAllPieceToChessBox() {
		for (int i = 0; i < redPiece.length; i++)
			redChessBox.putPieceToBox(redPiece[i]);
		for (int i = 0; i < blackPiece.length; i++)
			blackChessBox.putPieceToBox(blackPiece[i]);
		Point[] boxPoint = redChessBox.getBoxPoint();
		for (int i = 0; i < boxPoint.length; i++)
			boxPoint[i].setHaveChessPiece(true);
		boxPoint = blackChessBox.getBoxPoint();
		for (int i = 0; i < boxPoint.length; i++)
			boxPoint[i].setHaveChessPiece(true);
		for (int i = 0; i < playPoint.length; i++) {
			for (int j = 0; j < playPoint[i].length; j++) {
				playPoint[i][j].setHaveChessPiece(false);
			}
		}
	}
}