/*     */ package RCM.PropertiesLoader;
/*     */ 
/*     */ import RCM.RCM_Main;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AerofoilProperties
/*     */ {
/*  19 */   private float[] alpha = new float['ˑ'];
/*     */   
/*  21 */   private float[] thinPlateCl = new float['ˑ'];
/*  22 */   private float[] thinPlateCd = new float['ˑ'];
/*  23 */   private float[] thinPlateCm = new float['ˑ'];
/*     */   
/*  25 */   private float[] naca12Cl = new float['ˑ'];
/*  26 */   private float[] naca12Cd = new float['ˑ'];
/*  27 */   private float[] naca12Cm = new float['ˑ'];
/*     */   
/*  29 */   private float[] clarkyCl = new float['ˑ'];
/*  30 */   private float[] clarkyCd = new float['ˑ'];
/*  31 */   private float[] clarkyCm = new float['ˑ'];
/*     */   
/*     */   public void init(String name1, String name2, String name3)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/*  36 */     URL url = getClass().getResource(RCM_Main.propertiesFilePath + name1);
/*  37 */     InputStream inputstream = url.openStream();
/*  38 */     BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */     
/*     */ 
/*     */ 
/*  42 */     int place = 0;
/*     */     String line;
/*  44 */     while ((line = reader.readLine()) != null)
/*     */     {
/*     */ 
/*  47 */       this.alpha[place] = Float.valueOf(line.split(" ")[0]).floatValue();
/*     */       
/*  49 */       this.thinPlateCl[place] = Float.valueOf(line.split(" ")[1]).floatValue();
/*  50 */       this.thinPlateCd[place] = Float.valueOf(line.split(" ")[2]).floatValue();
/*  51 */       this.thinPlateCm[place] = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */       
/*  53 */       place++;
/*     */     }
/*     */     
/*  56 */     reader.close();
/*     */     
/*  58 */     place = 0;
/*     */     
/*  60 */     url = getClass().getResource(RCM_Main.propertiesFilePath + name2);
/*  61 */     inputstream = url.openStream();
/*  62 */     reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */     
/*  64 */     while ((line = reader.readLine()) != null)
/*     */     {
/*  66 */       this.naca12Cl[place] = Float.valueOf(line.split(" ")[1]).floatValue();
/*  67 */       this.naca12Cd[place] = Float.valueOf(line.split(" ")[2]).floatValue();
/*  68 */       this.naca12Cm[place] = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */       
/*  70 */       place++;
/*     */     }
/*     */     
/*  73 */     reader.close();
/*     */     
/*  75 */     place = 0;
/*     */     
/*  77 */     url = getClass().getResource(RCM_Main.propertiesFilePath + name3);
/*  78 */     inputstream = url.openStream();
/*  79 */     reader = new BufferedReader(new InputStreamReader(inputstream));
/*     */     
/*  81 */     while ((line = reader.readLine()) != null)
/*     */     {
/*  83 */       this.clarkyCl[place] = Float.valueOf(line.split(" ")[1]).floatValue();
/*  84 */       this.clarkyCd[place] = Float.valueOf(line.split(" ")[2]).floatValue();
/*  85 */       this.clarkyCm[place] = Float.valueOf(line.split(" ")[3]).floatValue();
/*     */       
/*  87 */       place++;
/*     */     }
/*     */     
/*  90 */     reader.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getThinPlateCl(float alp)
/*     */   {
/*  97 */     for (int i = 0; i < 720; i++)
/*     */     {
/*  99 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 105 */     float Cl = this.thinPlateCl[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.thinPlateCl[(i + 1)] - this.thinPlateCl[i]);
/*     */     
/* 107 */     return Cl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getThinPlateCd(float alp)
/*     */   {
/* 114 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 116 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 122 */     float Cd = this.thinPlateCd[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.thinPlateCd[(i + 1)] - this.thinPlateCd[i]);
/*     */     
/* 124 */     return Cd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getThinPlateCm(float alp)
/*     */   {
/* 131 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 133 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 139 */     float Cm = this.thinPlateCm[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.thinPlateCm[(i + 1)] - this.thinPlateCm[i]);
/*     */     
/* 141 */     return Cm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getNaca12Cl(float alp)
/*     */   {
/* 148 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 150 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 156 */     float Cl = this.naca12Cl[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.naca12Cl[(i + 1)] - this.naca12Cl[i]);
/*     */     
/* 158 */     return Cl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getNaca12Cd(float alp)
/*     */   {
/* 165 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 167 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 173 */     float Cd = this.naca12Cd[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.naca12Cd[(i + 1)] - this.naca12Cd[i]);
/*     */     
/* 175 */     return Cd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getNaca12Cm(float alp)
/*     */   {
/* 182 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 184 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 190 */     float Cm = this.naca12Cm[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.naca12Cm[(i + 1)] - this.naca12Cm[i]);
/*     */     
/* 192 */     return Cm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getClarkYCl(float alp)
/*     */   {
/* 199 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 201 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 207 */     float Cl = this.clarkyCl[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.clarkyCl[(i + 1)] - this.clarkyCl[i]);
/*     */     
/* 209 */     return Cl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getClarkYCd(float alp)
/*     */   {
/* 216 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 218 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 224 */     float Cd = this.clarkyCd[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.clarkyCd[(i + 1)] - this.clarkyCd[i]);
/*     */     
/* 226 */     return Cd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getClarkYCm(float alp)
/*     */   {
/* 233 */     for (int i = 0; i < 720; i++)
/*     */     {
/* 235 */       if ((alp >= this.alpha[i]) && (alp <= this.alpha[(i + 1)])) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 241 */     float Cm = this.clarkyCm[i] + (alp - this.alpha[i]) / (this.alpha[(i + 1)] - this.alpha[i]) * (this.clarkyCm[(i + 1)] - this.clarkyCm[i]);
/*     */     
/* 243 */     return Cm;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/PropertiesLoader/AerofoilProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */