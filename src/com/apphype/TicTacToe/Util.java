package com.apphype.TicTacToe;

import android.content.Context;
import android.widget.Toast;


public class Util {

	public static String UserName;
	
	public static char checkForWin(char[][] ARRAY){
	  if(ARRAY[0][0]!='-' && ARRAY[0][0] == ARRAY[0][1] && ARRAY[0][0] == ARRAY[0][2]){
		  return ARRAY[0][0];
	  }else if(ARRAY[1][0]!='-' && ARRAY[1][0] == ARRAY[1][1] && ARRAY[1][0] == ARRAY[1][2]){
		  return ARRAY[1][0];  
	  }else if(ARRAY[2][0]!='-' && ARRAY[2][0] == ARRAY[2][1] && ARRAY[2][0] == ARRAY[2][2]){
		  return ARRAY[2][0];
	  }else if(ARRAY[0][0]!='-' && ARRAY[0][0] == ARRAY[1][0] && ARRAY[0][0] == ARRAY[2][0]){
		  return ARRAY[0][0];  
	  }else if(ARRAY[0][1]!='-' && ARRAY[0][1] == ARRAY[1][1] && ARRAY[0][1] == ARRAY[2][1]){
		  return ARRAY[0][1] ;
	  }else if(ARRAY[0][2]!='-' && ARRAY[0][2] == ARRAY[1][2] && ARRAY[0][2] == ARRAY[2][2]){
		  return ARRAY[0][2];
	  }else if(ARRAY[0][0]!='-' && ARRAY[0][0] == ARRAY[1][1] && ARRAY[0][0] == ARRAY[2][2]){
		  return ARRAY[0][0];
	  }else if(ARRAY[0][2]!='-' && ARRAY[0][2] == ARRAY[1][1] && ARRAY[0][2] == ARRAY[2][0]){
		  return ARRAY[0][2];
	  }  
	  return '-';
	}
	
	public static boolean hasEmptyPlace(char[][] ARRAY){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(ARRAY[i][j] == '-'){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void showToastAlert(Context ctx, String message){
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
	
	
	

}
