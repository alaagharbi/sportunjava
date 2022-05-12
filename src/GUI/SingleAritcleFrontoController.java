/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javax.swing.text.StyleConstants.Bold;
import model.Article;
import model.Commentaire;
import model.PostLikes;
import service.ServiceArticle;
import service.ServiceCommentaire;
import sportunjava.SportunJava;
import utils.Statics;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 * FXML Controller class
 *
 * @author alaagha
 */
public class SingleAritcleFrontoController implements Initializable {

    @FXML
    private Text txtdescription;
    @FXML
    private Button btliko;
    @FXML
    private ImageView btlikes;
    @FXML
    private Label lLikes;
    @FXML
    private Button btds;
    @FXML
    private ImageView btdislikes;
    @FXML
    private Label lcommentaires;
    @FXML
    private Label lDate;
    @FXML
    private Label lhashtag;
    @FXML
    private Label lArticleTitre;
    @FXML
    private ImageView ivArt;
    @FXML
    private Text txtArt;
    @FXML
    private Button trnsalate;
    @FXML
    private Text trannstxt;
    @FXML
    private VBox vbcom;

        private Article article;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         String usrname="usrname";
        int artid=Statics.ART.getId();
        ListView<VBox> li=new ListView<>();
    ServiceArticle sa=new ServiceArticle();
        article=sa.ArticlleDetail(artid);
       
        lArticleTitre.setText(article.getTitre());
        txtdescription.setText(article.getDescription());
        txtArt.setText(article.getText());
        lhashtag.setText(article.getTag());
        lDate.setText(article.getCreatedAt());
        lLikes.setText(sa.likesCount(artid)+" likes");
        if(sa.PoslikedByUser(artid, 2))
        {btliko.setVisible(false);
        btds.setVisible(true);
        }else{
        btliko.setVisible(true);
        btds.setVisible(false);
        }
       btliko.setOnAction(event ->{
            try { PostLikes p=new PostLikes();
                p.setArticleId(artid);
                p.setUserId(2);
                sa.liker(p);
                URL fxUL = getClass().getResource("../GUI/SingleAritcleFronto.fxml");
                Parent root = FXMLLoader.load(fxUL);
                Stage win = (Stage) lLikes.getScene().getWindow();
                win.setScene(new Scene(root));
            } catch (IOException ex) {
                Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
                             });
              btds.setOnAction(event ->{
            try { PostLikes p=new PostLikes();
                p.setArticleId(artid);
                p.setUserId(2);
                sa.supprimerlikes(p.getArticleId(),p.getUserId());
                URL fxUL = getClass().getResource("../GUI/SingleAritcleFronto.fxml");                
                Parent root = FXMLLoader.load(fxUL);
                Stage win = (Stage) lLikes.getScene().getWindow();
                win.setScene(new Scene(root));
            } catch (IOException ex) {
                Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
                             });
File im=new File(Statics.RelativeURL+"\\"+article.getMedia());
        try {
            System.out.println(im.toURI().toURL().toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ivArt.setImage(new Image(im.toURI().toURL().toString()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
                   // ivArt.setFitHeight(666);
                 //   ivArt.setFitWidth(298);
        System.out.println(sa.likesCount(artid));
        
        
        
       ServiceCommentaire sc =  new ServiceCommentaire();
        List<Commentaire> lscomnnet = sc.afficherCommentArticle(artid);
        Commentaire com=new Commentaire();
                     Label labusr = new Label(usrname);
                             TextField txAddCom=new TextField();
                             Button btajcom=new Button();
                             btajcom.setText("ajouter Commentaire");
                             btajcom.setOnAction(event->{
            try {
                com.setArticleid(artid);
                com.setUsername(usrname);
                com.setText(txAddCom.getText());
                sc.ajouter(com);
                URL fxUL = getClass().getResource("../GUI/SingleAritcleFronto.fxml");
                
                Parent root = FXMLLoader.load(fxUL);
                Stage win = (Stage) btajcom.getScene().getWindow();
                win.setScene(new Scene(root));
            } catch (IOException ex) {
                Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
                             });
VBox verbo = new VBox();
            labusr.setPrefWidth(300);
            txAddCom.setPrefWidth(300);
//            txAddCom.setBackground(new Background(new BackgroundFill(Color.web("#393351"), CornerRadii.EMPTY, Insets.EMPTY)));
                           HBox herbb=new HBox();    
        btajcom.setStyle("-fx-background-color: #f23849");
                           Label labe = new Label();
         verbo.getChildren().addAll(labusr,txAddCom,btajcom);
            herbb.getChildren().addAll(verbo);
            herbb.setPrefWidth(vbcom.getMaxWidth());
            herbb.getStyleClass().add("color-palette");
            herbb.setBackground(new Background(new BackgroundFill(Color.web("#393351"), CornerRadii.EMPTY, Insets.EMPTY)));
            vbcom.getChildren().addAll(herbb,labe);
        int i = 0;
        for(Commentaire elem: lscomnnet){
            Label lab = new Label();
            Label lab1 = new Label();
               Label lab3 = new Label();
               HBox herbox = new HBox();

               Button btsupprimer = new Button("supprimer");
               btsupprimer.setBackground(Background.EMPTY);
               btsupprimer.setOnAction(event ->{
    try {
        sc.supprimer(elem);
        URL fxURL = getClass().getResource("../GUI/SingleAritcleFronto.fxml");
        
        Parent root = FXMLLoader.load(fxURL);
        Stage win = (Stage) btsupprimer.getScene().getWindow();
        win.setScene(new Scene(root));
        
    } catch (IOException ex) {
        Logger.getLogger(ListCommentsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
               });
               Button btmodifer = new Button("mdifier");
               btmodifer.setBackground(Background.EMPTY);
               btmodifer.setOnAction(event ->{
    try {
        URL fxURL = getClass().getResource("../GUI/ModifierCommentaire.fxml");
                Statics.COM=elem;
        Parent root = FXMLLoader.load(fxURL);
        Stage win = (Stage) btmodifer.getScene().getWindow();
        win.setScene(new Scene(root));
        
    } catch (IOException ex) {
        Logger.getLogger(ListCommentsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
               });
            //   ImageView imgId=new ImageView();
              // imgId.setImage(new Image("../blogimages/corbeille-removebg-preview.png"));
               // imgId.setFitHeight(34);
               // imgId.setFitWidth(34);
              // btsupprimer.setGraphic(imgId);
               VBox verbox = new VBox();
            lab.setText(elem.getUsername());
            lab.setTextFill(Color.web("#FA2C56"));
            lab.setPrefWidth(300);
            System.out.println(elem.getUsername());
            lab1.setText(elem.getText());
            lab1.setTextFill(Color.BLANCHEDALMOND);
            lab1.setText(cencor(elem.getText()));
            lab1.setPrefWidth(300);
            verbox.getChildren().addAll(lab,lab1);
            
            if(elem.getUsername().equals(usrname)){
                btsupprimer.setTextFill(Color.web("#FA2C56"));
                btmodifer.setTextFill(Color.web("#FFFFFF"));
            herbox.getChildren().addAll(verbox,btsupprimer,btmodifer);
            }else
            {
            herbox.getChildren().addAll(verbox);
            }
            herbox.setPrefWidth(vbcom.getMaxWidth());
            herbox.getStyleClass().add("color-palette");
            herbox.setBackground(new Background(new BackgroundFill(Color.web("#393351"), CornerRadii.EMPTY, Insets.EMPTY)));
            vbcom.getChildren().addAll(herbox,lab3);
        }
        trnsalate.setOnAction(events->{
            String sss=trans(article.getText());
            trannstxt.setText(sss);
                  System.out.println(sss);
        });
    }    
    public String cencor(String str){
        String filtr="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType,str);
            
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/bad_words?censor_character=*")
                    .addHeader("apikey", "mTJ9KbWtFgygUnzeNupYFhifDOeMkCSL")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
                        JSONObject jArray = new JSONObject(response.body().string());
                  String deviceId = (String) jArray.toString();
                  System.out.println(deviceId);
                 return jArray.get("censored_content").toString();
                    
               } catch (IOException ex) {
            Logger.getLogger(SportunJava.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return filtr;
    }
    public String trans(String st){
        String stt="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, st);
            Request request = new Request.Builder() .url("https://api.apilayer.com/language_translation/translate?target=en") 
                     .addHeader("apikey", "XSMFQDr2F7FEEYvsg2TpxnE7CXeF4lvb")
                     .method("POST", body) .build(); 
            Response response = client.newCall(request).execute();
                     JSONObject jArray =(JSONObject) new JSONObject(response.body().string());
                     JSONArray jArray3 = jArray.getJSONArray("translations");
                     String dev="";
                     for(int i = 0; i < jArray3 .length(); i++)
{
   JSONObject object3 = jArray3.getJSONObject(i);
   dev = object3.getString("translation");

}
                    return dev;
        } catch (IOException ex) {
            Logger.getLogger(SingleAritcleFrontoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stt;
    }

    @FXML
    private void closewin(MouseEvent event) {
    }
    
}
