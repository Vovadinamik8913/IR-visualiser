import graphviz
import pathlib
import subprocess


script_dir = pathlib.Path(__file__).parent

dot_dir = script_dir/ 'dot_files'
dot_dir.mkdir(exist_ok=True)

subprocess.run(args=["opt" ,"-passes=dot-cfg" ,"../ir/avia.ll"], cwd=str(dot_dir))