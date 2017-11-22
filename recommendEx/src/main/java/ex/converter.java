package ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class converter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		  BufferedReader br = new BufferedReader(new FileReader("/Users/user/Desktop/project/intern/intern/recommendEx/src/main/java/ex/data/u.data"));

          BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/user/Desktop/project/intern/intern/recommendEx/src/main/java/ex/data/movies.csv"));

         

          String line;

          while((line = br.readLine()) != null) {

                       String[] values = line.split("\\t", -1);

                       bw.write(values[0] + "," + values[1] + "," + values[2] + "\n");

          }                       

          br.close();

          bw.close();
	}

}
