package com.dipo33.bewitched.mixin;

import com.dipo33.bewitched.block.BlockBewitchedLeaves;
import com.dipo33.bewitched.init.BewitchedBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Inject(method = "loadRenderers", at = @At("HEAD"))
    private void bewitched$syncLeafGraphics(CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null) {
            ((BlockBewitchedLeaves) BewitchedBlocks.LEAVES.get())
                .setGraphicsLevel(mc.gameSettings.fancyGraphics);
        }
    }
}
