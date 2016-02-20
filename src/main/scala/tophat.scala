package ohnosequencesBundles.statika

import ohnosequences.statika._
import java.io.File

abstract class Tophat(val version: String, val bowtie2: Bowtie2)
  extends Bundle(bowtie2) { tophat =>

  val name = s"tophat-${version}.Linux_x86_64"
  val tarGz = name + ".tar.gz"

  val binaries: Set[String] = Set(
    "bam2fastx",
    "bam_merge",
    "bed_to_juncs",
    "contig_to_chr_coords",
    "fix_map_ordering",
    "gtf_juncs",
    "gtf_to_fasta",
    "juncs_db",
    "long_spanning_reads",
    "map2gtf",
    "prep_reads",
    "sam_juncs",
    "segment_juncs",
    "sra_to_solid",
    "tophat",
    "tophat2",
    "tophat-fusion-post",
    "tophat_reports"
  )

  lazy val download: CmdInstructions = cmd("wget")(
    s"http://s3-eu-west-1.amazonaws.com/resources.ohnosequences.com/tophat/${version}/${tophat.tarGz}"
  )

  lazy val untar: CmdInstructions = cmd("tar")("-xvzf", tophat.tarGz)

  def linkCommand(binary: String): CmdInstructions = cmd("ln")("-s",
    new File(tophat.name, binary).getCanonicalPath,
    s"/usr/bin/${binary}"
  )

  def instructions: AnyInstructions =
    download -&-
    untar -&-
    binaries.foldLeft[AnyInstructions]( Seq("echo", "linking tophat binaries") ){ _ -&- linkCommand(_) }

}
