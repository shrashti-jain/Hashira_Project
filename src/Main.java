import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String fileName = "input.json";
        System.out.println("Processing " + fileName + ":");

        Gson gson = new Gson();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JsonObject rootObj = gson.fromJson(jsonContent, JsonObject.class);

            JsonObject keysObj = rootObj.getAsJsonObject("keys");
            int n = keysObj.get("n").getAsInt();
            int k = keysObj.get("k").getAsInt();

            Map<Integer, Map<String, String>> rootMap = new HashMap<>();
            Set<String> keySet = rootObj.keySet();
            for (String strKey : keySet) {
                if (!strKey.equals("keys")) {
                    int x = Integer.parseInt(strKey);
                    JsonObject item = rootObj.getAsJsonObject(strKey);
                    String baseStr = item.get("base").getAsString();
                    String valueStr = item.get("value").getAsString();
                    Map<String, String> pair = new HashMap<>();
                    pair.put("base", baseStr);
                    pair.put("value", valueStr);
                    rootMap.put(x, pair);
                }
            }

            List<Integer> sortedKeys = new ArrayList<>(rootMap.keySet());
            Collections.sort(sortedKeys);

            List<Integer> xs = new ArrayList<>();
            List<BigInteger> ys = new ArrayList<>();
            for (int i = 0; i < Math.min(k, sortedKeys.size()); i++) {
                int x = sortedKeys.get(i);
                Map<String, String> pair = rootMap.get(x);
                int base = Integer.parseInt(pair.get("base"));
                String valueStr = pair.get("value");
                BigInteger y = new BigInteger(valueStr, base);
                xs.add(x);
                ys.add(y);
            }

            int m = xs.size();

            BigInteger sumC = BigInteger.ZERO;
            for (int i = 0; i < m; i++) {
                BigInteger prodNum = BigInteger.ONE;
                BigInteger prodDen = BigInteger.ONE;
                for (int j = 0; j < m; j++) {
                    if (i != j) {
                        prodNum = prodNum.multiply(BigInteger.valueOf(-xs.get(j)));
                        prodDen = prodDen.multiply(BigInteger.valueOf(xs.get(i) - xs.get(j)));
                    }
                }

                BigInteger term = ys.get(i).multiply(prodNum).divide(prodDen);
                sumC = sumC.add(term);
            }

            System.out.println("The constant C is: " + sumC);

        } catch (IOException e) {
            System.err.println("Error reading " + fileName + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing " + fileName + ": " + e.getMessage());
        }
    }
}