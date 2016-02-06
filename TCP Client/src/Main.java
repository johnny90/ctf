import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Stream;

class Main {
  public static void main(String argv[]) throws Exception {
    long lower = 1;
    long higher = 128;
    long middle;
    String response = "";

    Socket clientSocket = new Socket("54.195.168.162", 31337);

    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    outToServer.writeBytes("street\n");
    System.out.println(inFromServer.readLine());
    System.out.println(inFromServer.readLine());
    System.out.println(inFromServer.readLine());

    while (!response.contains("done")){
      middle = halfWay(lower, higher);

      outToServer.writeBytes(String.valueOf(middle) + '\n');
      System.out.println(String.valueOf(middle));

      response = inFromServer.readLine();
      System.out.println(response);

      if (tooHigh(response)) {
        higher = middle  - 1;
      } else {
        lower = middle + 1;
      }
    }

    for (int i = 0; i < 2; i++) {
      response = inFromServer.readLine();
      System.out.println(response);
    }

    lower = 1;
    higher = 4294967296l;

    while (!response.contains("done")){
      middle = halfWay(lower, higher);

      outToServer.writeBytes(String.valueOf(middle) + '\n');
      System.out.println(String.valueOf(middle));

      response = inFromServer.readLine();
      System.out.println(response);

      if (tooHigh(response)) {
        higher = middle  - 1;
      } else {
        lower = middle + 1;
      }
    }

    for (int i = 0; i < 2; i++) {
      response = inFromServer.readLine();
      System.out.println(response);
    }

    clientSocket.close();
  }

  private static long halfWay(long lower, long higher) {
    return (lower + higher) / 2;
  }

  private static boolean tooHigh(String response) {
    return response.contains("high");
  }
}