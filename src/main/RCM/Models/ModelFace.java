/*    */ package RCM.Models;
/*    */ 
/*    */ public class ModelFace
/*    */ {
/*  5 */   private final int[] vertexIndices = { -1, -1, -1 };
/*  6 */   private final int[] normalIndices = { -1, -1, -1 };
/*  7 */   private final int[] textureCoordinateIndices = { -1, -1, -1 };
/*    */   
/*    */   public ModelFace(int[] vertexIndices, int[] normalIndices, int[] textureCoordinateIndices)
/*    */   {
/* 11 */     this.vertexIndices[0] = vertexIndices[0];
/* 12 */     this.vertexIndices[1] = vertexIndices[1];
/* 13 */     this.vertexIndices[2] = vertexIndices[2];
/* 14 */     this.textureCoordinateIndices[0] = textureCoordinateIndices[0];
/* 15 */     this.textureCoordinateIndices[1] = textureCoordinateIndices[1];
/* 16 */     this.textureCoordinateIndices[2] = textureCoordinateIndices[2];
/* 17 */     this.normalIndices[0] = normalIndices[0];
/* 18 */     this.normalIndices[1] = normalIndices[1];
/* 19 */     this.normalIndices[2] = normalIndices[2];
/*    */   }
/*    */   
/*    */   public int[] getVertexIndices()
/*    */   {
/* 24 */     return this.vertexIndices;
/*    */   }
/*    */   
/*    */   public int[] getTextureCoordinateIndices()
/*    */   {
/* 29 */     return this.textureCoordinateIndices;
/*    */   }
/*    */   
/*    */   public int[] getNormalIndices()
/*    */   {
/* 34 */     return this.normalIndices;
/*    */   }
/*    */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Models/ModelFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */