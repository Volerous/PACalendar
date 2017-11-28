import apiai
import pyaudio
import time
import ast
CLIENT_ACCESS_TOKEN = "a048f1b585764cb389b9291967d71e88"
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100
RECORD_SECONDS = 2
CHUNK = 512

def s2t():
    resampler = apiai.Resampler(source_samplerate=RATE)

    vad = apiai.VAD()

    ai = apiai.ApiAI(CLIENT_ACCESS_TOKEN, '')

    request = ai.voice_request()

    request.lang = 'en' # optional, default value equal 'en'

    def callback(in_data, frame_count, time_info, status):
        frames, data = resampler.resample(in_data, frame_count)
        state = vad.processFrame(frames)
        request.send(data)

        if (state == 1):
            return in_data, pyaudio.paContinue
        else:
            return in_data, pyaudio.paComplete

    p = pyaudio.PyAudio()

    stream = p.open(format=FORMAT,
                    channels=CHANNELS, 
                    rate=RATE, 
                    input=True,
                    output=False,
                    frames_per_buffer=CHUNK,
                    stream_callback=callback)

    stream.start_stream()

    print ("Say!")

    # try:
    while stream.is_active():
        time.sleep(0.1)
    # except Exception:
    #     raise Exception
    # except KeyboardInterrupt:
    #     pass

    stream.stop_stream()
    stream.close()
    p.terminate()

    print ("Wait for response...")
    response = request.getresponse()

    res = response.read()
    # resDict = ast.literal_eval(res)
    # print(resDict["result"]['resolvedQuery'])
    #print (response.read())
    print(res)
    # return resDict["result"]['resolvedQuery']
s2t()