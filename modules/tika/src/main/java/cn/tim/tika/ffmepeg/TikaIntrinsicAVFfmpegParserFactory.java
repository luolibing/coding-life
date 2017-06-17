
package cn.tim.tika.ffmepeg;

import cn.tim.tika.metadata.PBCore;
import cn.tim.tika.parser.FfmpegExternalParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.external.ExternalParser;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Creates instances of {@link ExternalParser} configured for FFmpeg
 * and {@link PBCore} metadata.
 */
public class TikaIntrinsicAVFfmpegParserFactory
{
    private static final String FFMPEG_OPTIONS = "-i ${INPUT}";
    public static final int NUM_SUPPORTED_TRACKS = 4;
    private static final String STREAM_PREFIX_FORMAT = "#0[:\\.]";
    
    private TikaIntrinsicAVFfmpegParserFactory()
    {
    }
    
    /**
     * Creates an {@link ExternalParser} configured for FFmpeg which
     * parses into {@link PBCore} metadata.
     *  
     * @param
     * @return the new Parser
     */
    public static FfmpegExternalParser createDefaultInstance(String... command) {

        FfmpegExternalParser parser = new FfmpegExternalParser();

        //String[] command = new String[] { runtimeFfmpegExecutable + " " + FFMPEG_OPTIONS };

        //String[] command = new String[] { runtimeFfmpegExecutable };

        HashMap<Pattern, String> extractionPatterns = new HashMap<Pattern, String>();
        
        extractionPatterns.put(
                Pattern.compile("Duration: (\\d+:\\d+:\\d+\\.?\\d?\\d?), "),
                PBCore.INSTANTIATION_DURATION.getName());
        extractionPatterns.put(
                Pattern.compile("Duration: .*bitrate: (\\d+ kb/s)"),
                PBCore.INSTANTIATION_DATA_RATE.getName());
        
        for (int i = 0; i < NUM_SUPPORTED_TRACKS; i++)
        {
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*: (\\w*):"),
                    PBCore.ESSENCE_TRACK_TYPE(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*: (\\w*)( \\(|,)"),
                    PBCore.ESSENCE_TRACK_ENCODING(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*, (\\d+ kb/s)"), 
                    PBCore.ESSENCE_TRACK_DATA_RATE(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*, (\\d+\\.?\\d+ fps)"), 
                    PBCore.ESSENCE_TRACK_FRAME_RATE(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*, (\\d+ Hz)"), 
                    PBCore.ESSENCE_TRACK_SAMPLING_RATE(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*, (\\d+x\\d+)"), 
                    PBCore.ESSENCE_TRACK_FRAME_SIZE(i).getName());
            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + ".*DAR (\\d+:\\d+)]"), 
                    PBCore.ESSENCE_TRACK_ASPECT_RATIO(i).getName());

            extractionPatterns.put(
                    Pattern.compile(STREAM_PREFIX_FORMAT + i + "\\((\\w+)\\)"), 
                    PBCore.ESSENCE_TRACK_LANGUAGE(i).getName());
        }

        String[] cmd = new String[command.length+2];
        System.arraycopy(command, 0, cmd, 0, command.length);
        cmd[cmd.length-2] = "-i";
        cmd[cmd.length-1] = "${INPUT}";
        parser.setCommand(cmd);
        parser.setMetadataExtractionPatterns(extractionPatterns);
        
        return parser;
    }

}
