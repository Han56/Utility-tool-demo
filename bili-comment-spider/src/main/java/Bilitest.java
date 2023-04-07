import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.CommentEntity;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han56
 * @description 功能描述
 * @create 2023/3/23 下午2:18
 */
public class Bilitest {

    public static void main(String[] args) throws IOException {
        String cid = "BV1XK4y1n7Vi"; // 评论所属的视频ID
        int pageNo = 30; // 评论的页数
        int pageSize = 20; // 每页的评论数

        List<CommentEntity> commentEntities = new ArrayList<>();
        //爬取数据
        for (int j=1;j<=pageNo;j++){
            try {
                String url = "https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=" + j + "&type=1&oid=" + cid + "&sort=0&nohot=1&ps=" + pageSize;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String commentJson = response.toString(); // 获取到的评论JSON数据

                //解析json数据
                JSONObject rootjson = JSONObject.parseObject(commentJson);
                JSONObject datajson = (JSONObject) rootjson.get("data");
                JSONArray repliesArray = datajson.getJSONArray("replies");
                for (Object o : repliesArray) {
                    JSONObject reply = (JSONObject) o;
                    JSONObject content = reply.getJSONObject("content");
                    String msg = content.getString("message");
                    CommentEntity commentEntity = new CommentEntity(msg);
                    commentEntities.add(commentEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //写入excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet= workbook.createSheet();
        HSSFRow header= sheet.createRow(0);
        HSSFCellStyle cellStyle= workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        HSSFCell cell= header.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("评论");

        for(int i=0;i< commentEntities.size();i++){
            HSSFRow content = sheet.createRow(i+1);
            content.createCell(0).setCellValue(commentEntities.get(i).getComment());
        }
        //写入文件
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/data/workarea/PythonArea/weizhendataset/bili_comments.xls");
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("导出完成！");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("导出失败！");
        }
    }
}
