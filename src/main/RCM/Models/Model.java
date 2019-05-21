/*     */ package RCM.Models;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.vecmath.Vector2f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Model
/*     */ {
/*  22 */   private List<Vector3f> vertices = new ArrayList();
/*  23 */   private List<Vector2f> textureCoordinates = new ArrayList();
/*  24 */   private List<Vector3f> normals = new ArrayList();
/*  25 */   private List<ModelFace> faces = new ArrayList();
/*     */   
/*     */   private int[] vbo;
/*     */   
/*     */ 
/*     */   public List<ModelFace> getFaces()
/*     */   {
/*  32 */     return this.faces;
/*     */   }
/*     */   
/*     */   public List<Vector3f> getVertices()
/*     */   {
/*  37 */     return this.vertices;
/*     */   }
/*     */   
/*     */   public List<Vector3f> getNormals()
/*     */   {
/*  42 */     return this.normals;
/*     */   }
/*     */   
/*     */   public List<Vector2f> getTextureCoordinates()
/*     */   {
/*  47 */     return this.textureCoordinates;
/*     */   }
/*     */   
/*     */   public void loadModel(String path, String name) throws FileNotFoundException, IOException
/*     */   {
/*  52 */     URL url = Model.class.getResource(path);
/*  53 */     InputStream inputstream = url.openStream();
/*  54 */     BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */     
/*  56 */     boolean flag = false;
/*  57 */     Model model = new Model();
/*     */     
/*     */ 
/*  60 */     while ((line = reader.readLine()) != null)
/*     */     {
/*  62 */       if ((line.startsWith("o ")) && (line.contains(name))) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  68 */     String line = reader.readLine();
/*     */     
/*  70 */     while ((line != null) && (!line.startsWith("e ")))
/*     */     {
/*     */ 
/*  73 */       if (line.startsWith("v "))
/*     */       {
/*  75 */         float x = Float.valueOf(line.split(" ")[1]).floatValue();
/*  76 */         float y = Float.valueOf(line.split(" ")[2]).floatValue();
/*  77 */         float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*  78 */         this.vertices.add(new Vector3f(x, y, z));
/*     */       }
/*  80 */       else if (line.startsWith("t "))
/*     */       {
/*  82 */         float x = Float.valueOf(line.split(" ")[1]).floatValue();
/*  83 */         float y = -Float.valueOf(line.split(" ")[2]).floatValue();
/*  84 */         this.textureCoordinates.add(new Vector2f(x, y));
/*     */       }
/*  86 */       else if (line.startsWith("n "))
/*     */       {
/*  88 */         float x = Float.valueOf(line.split(" ")[1]).floatValue();
/*  89 */         float y = Float.valueOf(line.split(" ")[2]).floatValue();
/*  90 */         float z = Float.valueOf(line.split(" ")[3]).floatValue();
/*  91 */         this.normals.add(new Vector3f(x, y, z));
/*     */ 
/*     */       }
/*  94 */       else if (line.startsWith("f "))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  99 */         int[] vertexIndices = { Integer.valueOf(line.split(" ")[1].split("/")[0]).intValue(), Integer.valueOf(line.split(" ")[2].split("/")[0]).intValue(), Integer.valueOf(line.split(" ")[3].split("/")[0]).intValue() };
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 104 */         int[] textureCoordinateIndices = {Integer.valueOf(line.split(" ")[1].split("/")[1]).intValue(), Integer.valueOf(line.split(" ")[2].split("/")[1]).intValue(), Integer.valueOf(line.split(" ")[3].split("/")[1]).intValue() };
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 109 */         int[] normalIndices = {Integer.valueOf(line.split(" ")[1].split("/")[2]).intValue(), Integer.valueOf(line.split(" ")[2].split("/")[2]).intValue(), Integer.valueOf(line.split(" ")[3].split("/")[2]).intValue() };
/* 110 */         this.faces.add(new ModelFace(vertexIndices, normalIndices, textureCoordinateIndices));
/*     */       }
/*     */       
/* 113 */       line = reader.readLine();
/*     */     }
/*     */     
/* 116 */     reader.close();
/* 117 */     inputstream.close();
/*     */   }
/*     */   
/*     */   public int[] getVBO()
/*     */   {
/* 122 */     return this.vbo;
/*     */   }
/*     */   
/*     */   public void createVBO()
/*     */   {
/* 127 */     int vboVertexHandle = GL15.glGenBuffers();
/* 128 */     int vboNormalHandle = GL15.glGenBuffers();
/* 129 */     int vboTextureHandler = GL15.glGenBuffers();
/*     */     
/* 131 */     FloatBuffer textCoord = reserveData(this.faces.size() * 6);
/* 132 */     FloatBuffer norm = reserveData(this.faces.size() * 9);
/* 133 */     FloatBuffer vert = reserveData(this.faces.size() * 9);
/*     */     
/* 135 */     for (ModelFace face : this.faces)
/*     */     {
/* 137 */       textCoord.put(asFloats((Vector2f)this.textureCoordinates.get(face.getTextureCoordinateIndices()[0] - 1)));
/* 138 */       textCoord.put(asFloats((Vector2f)this.textureCoordinates.get(face.getTextureCoordinateIndices()[1] - 1)));
/* 139 */       textCoord.put(asFloats((Vector2f)this.textureCoordinates.get(face.getTextureCoordinateIndices()[2] - 1)));
/* 140 */       norm.put(asFloats((Vector3f)this.normals.get(face.getNormalIndices()[0] - 1)));
/* 141 */       norm.put(asFloats((Vector3f)this.normals.get(face.getNormalIndices()[1] - 1)));
/* 142 */       norm.put(asFloats((Vector3f)this.normals.get(face.getNormalIndices()[2] - 1)));
/* 143 */       vert.put(asFloats((Vector3f)this.vertices.get(face.getVertexIndices()[0] - 1)));
/* 144 */       vert.put(asFloats((Vector3f)this.vertices.get(face.getVertexIndices()[1] - 1)));
/* 145 */       vert.put(asFloats((Vector3f)this.vertices.get(face.getVertexIndices()[2] - 1)));
/*     */     }
/*     */     
/* 148 */     vert.flip();
/* 149 */     norm.flip();
/* 150 */     textCoord.flip();
/*     */     
/* 152 */     GL15.glBindBuffer(34962, vboTextureHandler);
/* 153 */     GL15.glBufferData(34962, textCoord, 35044);
/* 154 */     GL11.glTexCoordPointer(2, 5126, 0, 0L);
/* 155 */     GL15.glBindBuffer(34962, vboNormalHandle);
/* 156 */     GL15.glBufferData(34962, norm, 35044);
/* 157 */     GL11.glNormalPointer(5126, 0, 0L);
/* 158 */     GL15.glBindBuffer(34962, vboVertexHandle);
/* 159 */     GL15.glBufferData(34962, vert, 35044);
/* 160 */     GL11.glVertexPointer(3, 5126, 0, 0L);
/* 161 */     GL15.glBindBuffer(34962, 0);
/*     */     
/* 163 */     this.vbo = new int[] { vboVertexHandle, vboNormalHandle, vboTextureHandler };
/*     */   }
/*     */   
/*     */   private FloatBuffer reserveData(int size)
/*     */   {
/* 168 */     return BufferUtils.createFloatBuffer(size);
/*     */   }
/*     */   
/*     */   private float[] asFloats(Vector3f v)
/*     */   {
/* 173 */     return new float[] { v.x, v.y, v.z };
/*     */   }
/*     */   
/*     */ 
/*     */   private float[] asFloats(Vector2f v)
/*     */   {
/* 179 */     return new float[] { v.x, -v.y };
/*     */   }
/*     */   
/*     */   public void draw()
/*     */   {
/* 184 */     for (ModelFace face : this.faces)
/*     */     {
/* 186 */       Vector2f t1 = (Vector2f)getTextureCoordinates().get(face.getTextureCoordinateIndices()[0] - 1);
/* 187 */       GL11.glTexCoord2f(t1.x, -t1.y);
/* 188 */       Vector3f n1 = (Vector3f)getNormals().get(face.getNormalIndices()[0] - 1);
/* 189 */       GL11.glNormal3f(n1.x, n1.y, n1.z);
/* 190 */       Vector3f v1 = (Vector3f)getVertices().get(face.getVertexIndices()[0] - 1);
/* 191 */       GL11.glVertex3f(v1.x, v1.y, v1.z);
/* 192 */       Vector2f t2 = (Vector2f)getTextureCoordinates().get(face.getTextureCoordinateIndices()[1] - 1);
/* 193 */       GL11.glTexCoord2f(t2.x, -t2.y);
/* 194 */       Vector3f n2 = (Vector3f)getNormals().get(face.getNormalIndices()[1] - 1);
/* 195 */       GL11.glNormal3f(n2.x, n2.y, n2.z);
/* 196 */       Vector3f v2 = (Vector3f)getVertices().get(face.getVertexIndices()[1] - 1);
/* 197 */       GL11.glVertex3f(v2.x, v2.y, v2.z);
/* 198 */       Vector2f t3 = (Vector2f)getTextureCoordinates().get(face.getTextureCoordinateIndices()[2] - 1);
/* 199 */       GL11.glTexCoord2f(t3.x, -t3.y);
/* 200 */       Vector3f n3 = (Vector3f)getNormals().get(face.getNormalIndices()[2] - 1);
/* 201 */       GL11.glNormal3f(n3.x, n3.y, n3.z);
/* 202 */       Vector3f v3 = (Vector3f)getVertices().get(face.getVertexIndices()[2] - 1);
/* 203 */       GL11.glVertex3f(v3.x, v3.y, v3.z);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Models/Model.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */