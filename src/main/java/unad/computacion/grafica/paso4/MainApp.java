package unad.computacion.grafica.paso4;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class MainApp extends Application
{
    double anchorX, anchorY, anchorAngle;
    
    public static void main(String[] args) 
    {
            Application.launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
        // TriangleMesh permite construir cualquier objeto tridimensional
        // utilizando triángulos. En este caso se va a crear una pirámide
        // de base cuadrada.
        TriangleMesh piramideMesh = new TriangleMesh();
        piramideMesh.getTexCoords().addAll(0,0);

        // Se definen los vértices de la pirámide. Una pirámide de base 
        // triangular tiene 5 vertices: 4 para la base cuadrada y 1 que
        // es el vértice superior de la pirámide.
        // La base cuadrada de la pirámide se va a dibujar en el plano xz, 
        // de modo que todos los puntos que definen este cuadrado tienen
        // y = 0 y solo es necesario definir las coordenas xy de cada punto
        // basado en el tamaño l del cuadrado base
        int l = 100;
        int h = -100;
        piramideMesh.getPoints().addAll(
                0,    h,    0, // Vértice superior de la pirámide
                l/2,  0,  l/2, // Vértice 1 del cuadrado base
                -l/2, 0,  l/2, // Vértice 2 del cuadrado base
                -l/2, 0, -l/2, // Vértice 3 del cuadrado base
                 l/2, 0, -l/2  // Vértice 4 del cuadrado base
        );

        // Ahora es necesario definir los triángulos que conforman la pirámide
        // Tengáse en cuenta que la base se define con dos triángulos para 
        // formar el cuadrado, de modo que en total son 6 triángulos.
        piramideMesh.getFaces().addAll(
                0,0,    1,0,    2,0,
                0,0,    2,0,    3,0,
                0,0,    3,0,    4,0,
                0,0,    4,0,    1,0,
                1,0,    2,0,    3,0,
                1,0,    3,0,    4,0
        );

        // Una textura de materia azul
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        // Ajustar la pirámide para visualizarla
        MeshView piramide = new MeshView(piramideMesh);
        piramide.setDrawMode(DrawMode.FILL);
        piramide.setRotationAxis(Rotate.Y_AXIS);
        piramide.setRotate(45);
        piramide.setMaterial(blueMaterial);
        piramide.setTranslateX(150);

        // Se incluye una luz para iluminar la escena
        PointLight light = new PointLight();
        light.setTranslateX(150);
        light.setTranslateY(-200);
        light.setTranslateZ(100);

        // Se crea una cámara para "ver" la escena
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(150);
        camera.setTranslateY(-75);
        camera.setTranslateZ(-400);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);

        // Adicionar la pirámide y la luz al grupo de objetos de la escena
        Group root = new Group(piramide, light);

        // Se crea la escena con los objetos
        Scene scene = new Scene(root, 400, 400, true);

        // Se registran los eventos del ratón, para rotar la pirámide con el
        // movimento del ratón. Recuérdese que el eje de rotación de la pirámide
        // se definió como el eje Y cuando esta se creó.
        scene.setOnMousePressed((MouseEvent event) -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngle = piramide.getRotate();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            piramide.setRotate(anchorAngle + anchorX - event.getSceneX());
        });

        // Y se adiciona la camara
        scene.setCamera(camera);

        stage.setScene(scene);            
        stage.setTitle("Computación Gráfica: Paso 4");            
        stage.show();		
    }
}
