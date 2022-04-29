/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.google.gson.*;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Article;
import model.Commentaire;
import model.PostLikes;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import service.ServiceArticle;
import service.ServiceCommentaire;
import sportunjava.SportunJava;
import utils.Statics;
import org.json.*;

/**
 * FXML Controller class
 *
 * @author alaagha
 */
public class SingleArticlFrantController implements Initializable {

    @FXML
    private Text singblogtitreid;
    @FXML
    private Text txtdescription;
    @FXML
    private ImageView btlikes;
    @FXML
    private ImageView btdislikes;
    @FXML
    private Label lLikes;
    @FXML
    private Label lcommentaires;
    @FXML
    private Label lhashtag;
        @FXML
    private Label lDate;
    @FXML
    private Label lArticleTitre;
@FXML
private ImageView ivArt;
    @FXML
    private VBox vbcom;
    private Article article;
    @FXML
    private Button btliko;
     @FXML
    private Button btds;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String usrname="usrname";
        int artid=3;
        ListView<VBox> li=new ListView<>();
    ServiceArticle sa=new ServiceArticle();
        article=sa.ArticlleDetail(artid);
        if(sa.PoslikedByUser(artid, 1)){
            File ig=new File("..\\blogimages\\like-removebg-preview.png");
            try {
                System.err.println(ig.toURI().toURL().toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
btlikes.setImage(new Image(ig.toURI().toURL().toString()));
btlikes.setFitWidth(43);
btlikes.setFitHeight(43);

            } catch (MalformedURLException ex) {
                Logger.getLogger(SingleArticlFrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lArticleTitre.setText(article.getTitre());
        txtdescription.setText(article.getDescription());
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
                URL fxUL = getClass().getResource("../GUI/SingleArticlFrant.fxml");
                //URL fxURL = getClass().getResource("../gui1/Payment.fxml");
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
                URL fxUL = getClass().getResource("../GUI/SingleArticlFrant.fxml");
                
                //URL fxURL = getClass().getResource("../gui1/Payment.fxml");
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
                    ivArt.setFitHeight(685);
                    ivArt.setFitWidth(276);
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
                URL fxUL = getClass().getResource("../GUI/SingleArticlFrant.fxml");
                
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
                           HBox herbb=new HBox();               
                           Label labe = new Label();
         verbo.getChildren().addAll(labusr,txAddCom,btajcom);
            herbb.getChildren().addAll(verbo);
            herbb.setPrefWidth(vbcom.getMaxWidth());
            herbb.getStyleClass().add("color-palette");
            herbb.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            vbcom.getChildren().addAll(herbb,labe);
        int i = 0;
        for(Commentaire elem: lscomnnet){
            Label lab = new Label();
            Label lab1 = new Label();
               Label lab3 = new Label();
               HBox herbox = new HBox();

               Button btsupprimer = new Button("supprimer");
               btsupprimer.setOnAction(event ->{
    try {
        sc.supprimer(elem);
        URL fxURL = getClass().getResource("../GUI/SingleArticlFrant.fxml");
        
        Parent root = FXMLLoader.load(fxURL);
        Stage win = (Stage) btsupprimer.getScene().getWindow();
        win.setScene(new Scene(root));
        
    } catch (IOException ex) {
        Logger.getLogger(ListCommentsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
               });
               Button btmodifer = new Button("supprimer");
               btmodifer.setOnAction(event ->{
    try {
        URL fxURL = getClass().getResource("../GUI/ModifierCommentaire.fxml");
        
        Parent root = FXMLLoader.load(fxURL);
        Stage win = (Stage) btmodifer.getScene().getWindow();
        win.setScene(new Scene(root));
        
    } catch (IOException ex) {
        Logger.getLogger(ListCommentsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
               });
//               ImageView imgId=new ImageView();
//               imgId.setImage(new Image("..//blogimages//corbeille-removebg-preview.png"));
//               imgId.setFitHeight(34);
//               imgId.setFitWidth(34);
               VBox verbox = new VBox();
            lab.setText(elem.getUsername());
            lab.setPrefWidth(300);
            System.out.println(elem.getUsername());
            lab1.setText(elem.getText());
           // lab1.setText(cencor(elem.getText()));
            lab1.setPrefWidth(300);
            verbox.getChildren().addAll(lab,lab1);
            if(elem.getUsername().equals(usrname)){
            herbox.getChildren().addAll(verbox,btsupprimer);
            }else
            {
            herbox.getChildren().addAll(verbox);
            }
            herbox.setPrefWidth(vbcom.getMaxWidth());
            herbox.getStyleClass().add("color-palette");
            herbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            vbcom.getChildren().addAll(herbox,lab3);
        }
    }    
    public String cencor(String str){
        String filtr="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType,str);
            
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/bad_words?censor_character=*")
                    .addHeader("apikey", "XSMFQDr2F7FEEYvsg2TpxnE7CXeF4lvb")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
                        JSONObject jArray = new JSONObject(response.body().string());
                     //               System.out.println(response.body().string());
                  String deviceId = (String) jArray.toString();
                  System.out.println(deviceId);
                 return jArray.get("censored_content").toString();
                    
               } catch (IOException ex) {
            Logger.getLogger(SportunJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filtr;
    }
}
