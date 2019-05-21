/*    */ package RCM.Physics;
/*    */ 
/*    */ import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
/*    */ import com.bulletphysics.collision.shapes.CollisionShape;
/*    */ import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynGroundBlock
/*    */ {
/*    */   private CollisionShape groundShape;
/*    */   
/*    */   public DynGroundBlock()
/*    */   {
/* 19 */     float TRIANGLE_SIZE = 1.0F;
/*    */     
/* 21 */     int vertStride = 12;
/* 22 */     int indexStride = 12;
/*    */     
/* 24 */     int NUM_VERTS_X = 2;
/* 25 */     int NUM_VERTS_Y = 2;
/* 26 */     int totalVerts = NUM_VERTS_X * NUM_VERTS_Y;
/*    */     
/* 28 */     int totalTriangles = 2 * (NUM_VERTS_X - 1) * (NUM_VERTS_Y - 1);
/*    */     
/*    */ 
/*    */ 
/* 32 */     ByteBuffer vertices = ByteBuffer.allocateDirect(totalVerts * vertStride).order(ByteOrder.nativeOrder());
/* 33 */     ByteBuffer gIndices = ByteBuffer.allocateDirect(totalTriangles * 3 * 4).order(ByteOrder.nativeOrder());
/*    */     
/* 35 */     Vector3f tmp = new Vector3f();
/*    */     
/* 37 */     for (int i = 0; i < NUM_VERTS_X; i++)
/*    */     {
/* 39 */       for (int j = 0; j < NUM_VERTS_Y; j++)
/*    */       {
/* 41 */         float height = 0.5F;
/*    */         
/* 43 */         tmp.set((i - NUM_VERTS_X * 0.5F) * 1.0F, height, (j - NUM_VERTS_Y * 0.5F) * 1.0F);
/*    */         
/*    */ 
/*    */ 
/* 47 */         int index = i + j * NUM_VERTS_X;
/* 48 */         vertices.putFloat((index * 3 + 0) * 4, tmp.x);
/* 49 */         vertices.putFloat((index * 3 + 1) * 4, tmp.y);
/* 50 */         vertices.putFloat((index * 3 + 2) * 4, tmp.z);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 55 */     gIndices.clear();
/* 56 */     for (int i = 0; i < NUM_VERTS_X - 1; i++)
/*    */     {
/* 58 */       for (int j = 0; j < NUM_VERTS_Y - 1; j++)
/*    */       {
/* 60 */         gIndices.putInt(j * NUM_VERTS_X + i);
/* 61 */         gIndices.putInt(j * NUM_VERTS_X + i + 1);
/* 62 */         gIndices.putInt((j + 1) * NUM_VERTS_X + i + 1);
/*    */         
/* 64 */         gIndices.putInt(j * NUM_VERTS_X + i);
/* 65 */         gIndices.putInt((j + 1) * NUM_VERTS_X + i + 1);
/* 66 */         gIndices.putInt((j + 1) * NUM_VERTS_X + i);
/*    */       }
/*    */     }
/*    */     
/* 70 */     gIndices.flip();
/*    */     
/* 72 */     TriangleIndexVertexArray indexVertexArrays = new TriangleIndexVertexArray(totalTriangles, gIndices, indexStride, totalVerts, vertices, vertStride);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 77 */     this.groundShape = new BvhTriangleMeshShape(indexVertexArrays, true);
/*    */   }
/*    */   
/*    */ 
/*    */   public CollisionShape getGroundShape()
/*    */   {
/* 83 */     return this.groundShape;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/DynGroundBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */