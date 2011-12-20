////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2010 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package macromedia.asc.util;

public interface CompilerProfiler
{
    /**
     *  Initialize the profiler.
     */
    public void initialize();

    /**
     *  Start allocation recording.
     *  @param sampling_delta - record every Nth allocation.
     *     Zero value records every allocation.
     *  @param threshold - record every allocation over the threshold.
     *     Zero value disables threshold-based recording.
     */
    public void startAllocationRecording(int sampling_delta, int threshold);

    /**
     *  Start CPU profiling.
     *  @param sample - use sample-based profiling.
     *    false value is interpreted by the implementation
     *    (often interpreted as exact profiling).
     */
    public void startCPUProfiling(boolean use_sampling_mode);
      
    /**
     *  Advance the memory allocation generation.
     */
    public void advanceGeneration(String description);

    /**
     *  Save a snapshot.
     *  @return a file path to the snapshot.
     */
    public String captureSnapshot();

    /**
     * Annotate a snapshot.
     * @param snapshot_file - file path to the snapshot.
     * @param annotation - the desired annoation.
     * @see captureSnapshot(), which returns the relevant file path.
     * @return true if the annotation succeeded.
     */
    public boolean annotateSnapshot(String snapshot_file, String annoation);
}
