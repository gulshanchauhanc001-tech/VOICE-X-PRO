# Engine research

- Android input sharing: https://developer.android.com/media/platform/sharing-audio-input — ordinary apps cannot both capture live input reliably; capture is not injection.
- https://github.com/papergray/MicUp — investigate actual routing implementation, MIT; Shizuku claims require device verification.
- https://github.com/xiaoxiaoyu-miao/MeowRVC — local ONNX RVC, MIT; speaker loopback is not virtual mic.
- https://github.com/ouor/RVC-Android — offline conversion reference; inspect license before reuse.
- https://github.com/riorgord/rvc-mobile — experimental root HAL; not suitable for default install.

Prioritize licensed model import, offline conversion benchmarks, then explicit device routing tests. Never modify game processes or claim game mic support until a receiving teammate verifies transformed audio. OEM voice changer remains a separate option. No upstream source has been integrated yet.
