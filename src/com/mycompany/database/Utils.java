package com.mycompany.database;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class Utils {
    public static BufferedImage getQR(int amount, String addInfo) {
        try {
            // URL API
            URL url = new URL("https://oop.dinhmanhhung.net/get_QR");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "amount": %d,
                    "addInfo": "%s",
                    "key": "dmhung1508"
                }
            """, amount, addInfo);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc ảnh trả về
                InputStream inputStream = connection.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);
                connection.disconnect();
                return image;
                
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}