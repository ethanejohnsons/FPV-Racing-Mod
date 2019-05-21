/*     */ package RCM.Physics;
/*     */ 
/*     */ public class MotorHandler { private float omega;
/*     */   private float torque;
/*     */   private float acceleration;
/*     */   private float inertia;
/*     */   private float startTorque;
/*     */   private float maxRPM;
/*     */   private float gearRatio;
/*     */   private boolean dmg;
/*  11 */   private int ID; private int controlChannel; private int sensorID; private float PI = 3.1415927F;
/*     */   private float motorAngle;
/*     */   private float applyTorque;
/*     */   
/*     */   public MotorHandler(int ID, float mass, float diameter, float pwr, float kVConstant, float inputVolts, float gearR, int senID, int channel)
/*     */   {
/*  17 */     this.ID = ID;
/*     */     
/*  19 */     this.omega = 0.0F;
/*  20 */     this.acceleration = 0.0F;
/*  21 */     this.inertia = (mass * diameter * diameter / 8.0F);
/*  22 */     this.controlChannel = channel;
/*  23 */     this.maxRPM = (kVConstant * inputVolts * 2.0F * this.PI / 60.0F);
/*  24 */     this.startTorque = (4.0F * pwr / this.maxRPM);
/*  25 */     this.gearRatio = gearR;
/*  26 */     this.motorAngle = 0.0F;
/*  27 */     this.applyTorque = 0.0F;
/*  28 */     this.sensorID = senID;
/*     */   }
/*     */   
/*     */   public void update(float resistTrqe, float inert, float requestSpeed, int driving, boolean wheelConnected, float dt, float alt)
/*     */   {
/*  33 */     if (!this.dmg)
/*     */     {
/*     */ 
/*  36 */       this.torque = resistTrqe;
/*     */       
/*     */ 
/*  39 */       if (requestSpeed != 0.0F)
/*     */       {
/*  41 */         this.torque -= this.omega * 1.0E-5F;
/*     */       }
/*     */       else
/*     */       {
/*  45 */         this.torque -= this.motorAngle * Math.abs(this.motorAngle) * 0.08F + this.omega * 1.2E-4F;
/*     */       }
/*     */       
/*  48 */       if ((this.omega < 6.2F) && (requestSpeed == 0.0F))
/*     */       {
/*  50 */         this.torque -= this.omega * 0.002F;
/*     */       }
/*     */       
/*     */ 
/*  54 */       if ((alt > 125.0F) || (requestSpeed == 0.0F))
/*     */       {
/*  56 */         this.applyTorque = 0.0F;
/*     */       }
/*     */       else
/*     */       {
/*  60 */         this.applyTorque = (-this.torque + 40.0F * (requestSpeed - getSpeedUnit()));
/*     */       }
/*     */       
/*  63 */       if (this.omega != 0.0F)
/*     */       {
/*  65 */         this.torque += getTorqueLimit(this.applyTorque) * this.gearRatio / driving;
/*     */       }
/*  67 */       else if (requestSpeed > 0.0F)
/*     */       {
/*  69 */         this.torque += this.startTorque * this.gearRatio / driving;
/*     */       }
/*  71 */       else if (requestSpeed < 0.0F)
/*     */       {
/*  73 */         this.torque -= this.startTorque * this.gearRatio / driving;
/*     */       }
/*     */       
/*  76 */       if (!wheelConnected)
/*     */       {
/*  78 */         this.acceleration = (this.torque / (this.inertia + inert));
/*  79 */         this.omega += this.acceleration * dt;
/*     */         
/*  81 */         setMotorAngle(dt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDmged(boolean dmg)
/*     */   {
/*  88 */     this.dmg = dmg;
/*     */     
/*  90 */     if (dmg)
/*     */     {
/*  92 */       this.omega = 0.0F;
/*  93 */       this.torque = 0.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   private void setMotorAngle(float dt)
/*     */   {
/*  99 */     this.motorAngle += this.omega * dt;
/*     */     
/* 101 */     while (this.motorAngle >= 0.19634955F)
/*     */     {
/* 103 */       this.motorAngle -= 0.3926991F;
/*     */     }
/* 105 */     while (this.motorAngle <= -0.19634955F)
/*     */     {
/* 107 */       this.motorAngle += 0.3926991F;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private float getTorqueLimit(float requiredTorque)
/*     */   {
/* 114 */     if (requiredTorque > 0.0F)
/*     */     {
/* 116 */       return Math.min(requiredTorque, this.startTorque * (1.0F - Math.abs(this.omega) / this.maxRPM));
/*     */     }
/* 118 */     if (requiredTorque < 0.0F)
/*     */     {
/* 120 */       return Math.max(requiredTorque, -this.startTorque * (1.0F - Math.abs(this.omega) / this.maxRPM));
/*     */     }
/*     */     
/* 123 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean isDmged()
/*     */   {
/* 128 */     return this.dmg;
/*     */   }
/*     */   
/*     */   public void setOmega(float omega)
/*     */   {
/* 133 */     this.omega = (omega * this.gearRatio);
/*     */   }
/*     */   
/*     */   public float getRotationalVel()
/*     */   {
/* 138 */     return this.omega / this.gearRatio;
/*     */   }
/*     */   
/*     */   public float getSpeedUnit()
/*     */   {
/* 143 */     return this.omega / this.maxRPM;
/*     */   }
/*     */   
/*     */   public float getTorque()
/*     */   {
/* 148 */     return this.torque;
/*     */   }
/*     */   
/*     */   public int getChannel()
/*     */   {
/* 153 */     return this.controlChannel;
/*     */   }
/*     */   
/*     */   public int getID()
/*     */   {
/* 158 */     return this.ID;
/*     */   }
/*     */   
/*     */   public int getSensorID()
/*     */   {
/* 163 */     return this.sensorID;
/*     */   }
/*     */ }


/* Location:              /Users/ethanejohnsons/Dropbox/The-RC-Mod-1.12.2.jar!/RCM/Physics/MotorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */