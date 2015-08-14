package ohnosequencesBundles.statika

import ohnosequences.statika._, bundles._, instructions._
import java.io.File

abstract class Tophat(val version: String, val bowtie2: Bowtie2) extends Bundle(bowtie2) {

  val usrbin = "/usr/bin/"
  val tophatDir = s"tophat-${version}"
  val tophatDistribution = s"tophat-${version}.Linux_x86_64"

  val commands: Set[String] = Set(
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

  def linkCommand(cmd: String): Results = Seq("ln", "-s", new File(s"${tophatDistribution}/${cmd}").getAbsolutePath, s"${usrbin}/${cmd}")


  def install: Results = {
    Seq("wget", s"http://s3-eu-west-1.amazonaws.com/resources.ohnosequences.com/tophat/${version}/${tophatDistribution}.tar.gz", "-O", s"${tophatDistribution}.tar.gz") ->-
    Seq("tar", "-xvzf", s"${tophatDistribution}.tar.gz") ->-
    commands.foldLeft[Results](
      Seq("echo", "linking tophat binaries")
    ){ (acc, cmd) => acc ->- linkCommand(cmd) } ->-
    success(s"${bundleName} is installed")
  }

}
