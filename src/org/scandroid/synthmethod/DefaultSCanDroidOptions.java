/**
 *
 * Copyright (c) 2009-2012,
 *
 *  Galois, Inc. (Aaron Tomb <atomb@galois.com>, 
 *                Rogan Creswick <creswick@galois.com>, 
 *                Adam Foltzer <acfoltzer@galois.com>)
 *  Steve Suh    <suhsteve@gmail.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. The names of the contributors may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package org.scandroid.synthmethod;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;

import org.scandroid.util.AndroidAnalysisContext;
import org.scandroid.util.ISCanDroidOptions;

import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.AnalysisOptions.ReflectionOptions;
import com.ibm.wala.ipa.callgraph.impl.DefaultContextSelector;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.util.io.FileProvider;

public abstract class DefaultSCanDroidOptions implements ISCanDroidOptions {

	@Override
	public boolean pdfCG() {
		return false;
	}

	@Override
	public boolean pdfPartialCG() {
		return false;
	}

	@Override
	public boolean pdfOneLevelCG() {
		return false;
	}

	@Override
	public boolean systemToApkCG() {
		return false;
	}

	@Override
	public boolean stdoutCG() {
		return true;
	}

	@Override
	public boolean includeLibrary() {
		// TODO is this right? we haven't summarized with CLI options set, so
		// this is what we've been doing...
		return true;
	}

	@Override
	public boolean separateEntries() {
		return false;
	}

	@Override
	public boolean ifdsExplorer() {
		return false;
	}

	@Override
	public boolean addMainEntrypoints() {
		return false;
	}

	@Override
	public boolean useThreadRunMain() {
		return false;
	}

	@Override
	public boolean stringPrefixAnalysis() {
		return false;
	}

	@Override
	public boolean testCGBuilder() {
		return false;
	}

	@Override
	public boolean useDefaultPolicy() {
		return false;
	}

	@Override
	public abstract URI getClasspath();

	@Override
	public String getFilename() {
		return new File(getClasspath()).getName();
	}

	@Override
	public URI getAndroidLibrary() {
		try {
			return new FileProvider().getResource("data/android-2.3.7_r1.jar")
					.toURI();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReflectionOptions getReflectionOptions() {
		return ReflectionOptions.NONE;
	}

	@Override
	public URI getSummariesURI() {
		try {
			return new FileProvider().getResource("data/MethodSummaries.xml")
					.toURI();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean classHierarchyWarnings() {
		return false;
	}

	@Override
	public boolean cgBuilderWarnings() {
		return false;
	}
	
	@Override
	public SSAPropagationCallGraphBuilder makeCallGraphBuilder(
			AnalysisScope scope, AnalysisOptions opts, AnalysisCache cache,
			ClassHierarchy cha, Collection<InputStream> extraSummaries) {
		return AndroidAnalysisContext.makeZeroCFABuilder(opts, cache, cha,
				scope, new DefaultContextSelector(opts, cha), null,
				extraSummaries, null);
	}

	public static String dumpString(ISCanDroidOptions options) {
		return "DefaultSCanDroidOptions [pdfCG()=" + options.pdfCG()
				+ ", pdfPartialCG()=" + options.pdfPartialCG()
				+ ", pdfOneLevelCG()=" + options.pdfOneLevelCG()
				+ ", systemToApkCG()=" + options.systemToApkCG()
				+ ", stdoutCG()=" + options.stdoutCG() + ", includeLibrary()="
				+ options.includeLibrary() + ", separateEntries()="
				+ options.separateEntries() + ", ifdsExplorer()="
				+ options.ifdsExplorer() + ", addMainEntrypoints()="
				+ options.addMainEntrypoints() + ", useThreadRunMain()="
				+ options.useThreadRunMain() + ", stringPrefixAnalysis()="
				+ options.stringPrefixAnalysis() + ", testCGBuilder()="
				+ options.testCGBuilder() + ", useDefaultPolicy()="
				+ options.useDefaultPolicy() + ", getClasspath()="
				+ options.getClasspath() + ", getFilename()="
				+ options.getFilename() + ", getAndroidLibrary()="
				+ options.getAndroidLibrary() + ", getReflectionOptions()="
				+ options.getReflectionOptions() + ", getSummariesURI()="
				+ options.getSummariesURI() + ", classHierarchyWarnings()="
				+ options.classHierarchyWarnings() + ", cgBuilderWarnings()="
				+ options.cgBuilderWarnings() + "]";
	}

}
