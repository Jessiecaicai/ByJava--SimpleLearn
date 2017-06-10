public class MoveRule implements java.io.Serializable {
	ChessPiece piece = null;//piece������ű��϶������ӵ�����
	Point[][] point;//�������ChessPoint���е�PlayPoint
	Point startPoint, endPoint;//����������ӵ������յ�

	public void setPoint(Point[][] point) {//���Խ�ChessPoint���е�PlayPoint���ô��ݸ�point
		this.point = point;
	}

	public boolean movePieceRule(ChessPiece piece, Point startPoint, Point endPoint) {
		//�����ж����Ӵ����startPoint�ߵ��յ�endPoint�Ƿ��������������
		int startI = -1, startJ = -1, endI = -1, endJ = -1;
		this.piece = piece;//��ChessPiece�е�piece��ŵı��϶������ӵ����ô��ݸ�piece
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		boolean isMove = false;
		for (int i = 0; i < point.length; i++) {//����
			for (int j = 0; j < point[i].length; j++) {//ֱ��
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
		
		//��ÿһ������ֱ����ֱ�ˡ����ߣ����޲��������Թ���
		if (piece.getName().equals("��") || piece.getName().equals("܇")) {
			if (startI == endI) {//ֱ�򲻱䣬������ʱ
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isHaveChessPiece()) {//�н�������������
						isMove = false;
						break;
					}
				}
				if (j == maxJ)
					isMove = true;
			} else if (startJ == endJ) { //������ȣ�������ʱ
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
		//�������֣��������������ߡ�
		else if (piece.getName().equals("��") || piece.getName().equals("�R")) {
			int xAxle = Math.abs(startI - endI);//ֱ���ֵ�ľ���ֵ
	           int yAxle=Math.abs(startJ-endJ);//�����ֵ�ľ���ֵ
	           if(xAxle==2&&yAxle==1){//ֱ2��1
	             if(endI>startI){//ǰ��
	                if(point[startI+1][startJ].isHaveChessPiece())//�м������ӣ�����
						isMove = false;
					else
						isMove = true;
				}
				if (endI < startI) {//����
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
		  //���ܹ��ӽ磬�����֣���������������
		else if (piece.getName().equals("��")) {
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endI <= 4) {//�������Ҳ��ܹ���  
				if (point[centerI][centerJ].isHaveChessPiece())//�����м�������
					isMove = false;
				else
					isMove = true;
			} else
				isMove = false;
		} else if (piece.getName().equals("��")) {
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
		 //�ڿ���ֱ��ֱ�˺��ߣ����޲������ڳ���ʱ�����һ��
		else if (piece.getName().equals("��")) {
			int number = 0;
			if (startI == endI) {//ֱ����ȣ�������ʱ
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
		//����û�й���ʱ��ֻ����ǰֱ��һ�������Ӻ󣬿���ǰֱ�߻����һ�������ܺ���
		else if (piece.getName().equals("��")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI >= 5) {//�����ӣ�������ǰ��һ�������ܺ���
				if (startI - endI == 1 && yAxle == 0)
					isMove = true;
				else
					isMove = false;
			} else if (endI <= 4) {//���ӣ�����ǰ�ɺ���
				if ((startI - endI == 1) && (yAxle == 0))
					isMove = true;
				else if ((endI - startI == 0) && (yAxle == 1))
					isMove = true;
				else
					isMove = false;
			}
		} else if (piece.getName().equals("��")) {
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
		 //ʿÿ��ֻ���ؾŹ�б�ߣ��ɽ����ˡ�
		else if (piece.getName().equals("ʿ") || piece.getName().equals("��")) {
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
			 * MoveRule ���е�һС�Σ�ԭ�д����޷�ִ�зɽ���������������˧��������м�	����������ʱ������˧�����ԳԵ�˧��������
			 * �޸ĺ����ִ�зɽ�����
			 */
			
			//˧��ÿ��ֻ����һ�����������߳��Ź���������ͬһֱ���ϣ�����رܡ�
		 else if ((piece.getName().equals("˧")) || (piece.getName().equals("��"))) {
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
					ok = point[endI][endJ].getChessPiece().getName().equals("˧")
							|| point[endI][endJ].getChessPiece().getName().equals("��");
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
